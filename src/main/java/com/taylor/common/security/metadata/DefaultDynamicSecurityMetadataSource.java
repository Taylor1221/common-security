package com.taylor.common.security.metadata;

import com.taylor.common.security.constant.PermissionType;
import com.taylor.common.security.model.PermissionRule;
import com.taylor.common.security.service.PermissionSourceService;
import com.taylor.common.security.white.WhiteListChecker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * <p>Spring Security 基于 URL 的访问控制 机制的核心组件之一</p>
 * <p>从请求的 URL 中提取访问权限信息，然后配合 {@link org.springframework.security.access.AccessDecisionManager} 进行权限验证</p>
 * <p>总的来说就是根据请求的路径，从configAttributeMap配置源对象中找到对应接口的权限对象配置信息</p>
 * @author loveCamille
 * @date 2025-04-03 11:27:08
 */
@Slf4j
@AllArgsConstructor
public class DefaultDynamicSecurityMetadataSource implements DynamicSecurityMetadataSource, InitializingBean{

    private final Map<AntPathRequestMatcher, Collection<ConfigAttribute>> permissionMap = new ConcurrentHashMap<>();

    private final PermissionSourceService permissionSourceService;

    private final WhiteListChecker whiteListChecker;

    /**
     * 加载权限并组装
     * @author loveCamille
     */
    @Override
    public void loadPermissions() {
        Map<AntPathRequestMatcher, Collection<ConfigAttribute>> newPermissionMap = buildPermissionMap();
        permissionMap.clear();
        permissionMap.putAll(newPermissionMap);
    }

    /**
     * 获取系统所有权限
     * @author loveCamille
     * @return {@link Map}
    */
    private Map<AntPathRequestMatcher, Collection<ConfigAttribute>> buildPermissionMap() {
        Map<AntPathRequestMatcher, Collection<ConfigAttribute>> permissionMap = new HashMap<>();
        for (PermissionRule rule : permissionSourceService.getAllPermissions(PermissionType.FUNC_PERMISSION)) {
            permissionMap.put(new AntPathRequestMatcher(rule.getUrlPattern()),
                    rule.getRoles().stream().map(SecurityConfig::new).collect(Collectors.toList()));
        }
        return permissionMap;
    }

    @Override
    public void afterPropertiesSet() {
        loadPermissions();
    }

    /**
     * 获取访问当前请求 URL 需要的权限
     * @author loveCamille
     * @param object 当前请求对象
     * @return {@link Collection<ConfigAttribute>} 所需权限信息
    */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) {
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        // 在白名单中，不需要任何权限
        if (whiteListChecker.isWhiteListed(request)) return Collections.emptyList();
        return permissionMap.entrySet().stream()
                .filter(entry -> entry.getKey().matches(request)) // 直接使用预编译的 `AntPathRequestMatcher`
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(Collections.emptyList());
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return Collections.emptyList();
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
