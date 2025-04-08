package com.taylor.common.security.config;

import com.taylor.common.security.manager.DynamicAccessDecisionManager;
import com.taylor.common.security.metadata.DefaultDynamicSecurityMetadataSource;
import com.taylor.common.security.metadata.DynamicSecurityMetadataSource;
import com.taylor.common.security.service.PermissionSourceService;
import com.taylor.common.security.white.AntPathWhiteListChecker;
import com.taylor.common.security.white.SecurityProperties;
import com.taylor.common.security.white.WhiteListChecker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 权限相关配置类（动态权限）
 *
 * @author loveCamille
 * @date 2025-04-07 14:15:45
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(PermissionSourceService.class)
public class DynamicPermissionConfiguration {

    @Bean
    @ConditionalOnMissingBean(WhiteListChecker.class)
    public WhiteListChecker whiteListChecker(SecurityProperties properties) {
        return new AntPathWhiteListChecker(properties);
    }

    @Bean
    @ConditionalOnMissingBean(DynamicSecurityMetadataSource.class)
    public DynamicSecurityMetadataSource dynamicSecurityMetadataSource(PermissionSourceService permissionSourceService,
                                                                       WhiteListChecker whiteListChecker) {
        return new DefaultDynamicSecurityMetadataSource(permissionSourceService, whiteListChecker);
    }

    @Bean
    public DynamicAccessDecisionManager dynamicAccessDecisionManager() {
        return new DynamicAccessDecisionManager();
    }

    @Bean
    public FilterSecurityInterceptorPostProcessor filterSecurityInterceptorPostProcessor(DynamicSecurityMetadataSource metadataSource,
                                                                      DynamicAccessDecisionManager accessDecisionManager) {
        return new FilterSecurityInterceptorPostProcessor(metadataSource, accessDecisionManager);
    }

}
