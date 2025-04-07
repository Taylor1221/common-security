package com.taylor.common.security.manager;

import cn.hutool.core.collection.CollUtil;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 动态权限决策管理
 *
 * @author loveCamille
 * @date 2025-04-03 11:24:20
 */
public class DynamicAccessDecisionManager implements AccessDecisionManager {

    /**
     * 判断用户的 Authentication 是否拥有对应的角色来决定是否允许访问
     * @author loveCamille
     * @param authentication 当前已认证的用户的认证信息，这个对象通常包含用户的身份、角色、权限信息等
     * @param object 当前正在访问的资源对象 这个对象通常是一个 HttpServletRequest（在 Web 环境中），代表 HTTP 请求
     * @param configAttributes 资源访问所需的权限/角色配置
    */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        // 当前资源不需要权限
        if (CollUtil.isEmpty(configAttributes)) return;
        Set<String> userRoles = authentication.getAuthorities().stream()
                .map(authority -> authority.getAuthority().toLowerCase()) // 统一转换为小写
                .collect(Collectors.toSet());
        for (ConfigAttribute configAttribute : configAttributes) {
            if (userRoles.contains(configAttribute.getAttribute().toLowerCase())) {
                return;
            }
        }
        throw new AccessDeniedException("抱歉，您没有访问权限");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
