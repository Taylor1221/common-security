package com.taylor.common.security.config;

import com.taylor.common.security.manager.DynamicAccessDecisionManager;
import com.taylor.common.security.metadata.DefaultDynamicSecurityMetadataSource;
import com.taylor.common.security.metadata.DynamicSecurityMetadataSource;
import com.taylor.common.security.service.PermissionSourceService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * 权限相关配置类（动态权限）
 *
 * @author loveCamille
 * @date 2025-04-07 14:15:45
 */
@Configuration
@ConditionalOnBean(PermissionSourceService.class)
public class DynamicPermissionConfiguration {

    @Bean
    @ConditionalOnMissingBean(DynamicSecurityMetadataSource.class)
    public DynamicSecurityMetadataSource dynamicSecurityMetadataSource(PermissionSourceService permissionSourceService) {
        return new DefaultDynamicSecurityMetadataSource(permissionSourceService);
    }

    @Bean
    public DynamicAccessDecisionManager dynamicAccessDecisionManager() {
        return new DynamicAccessDecisionManager();
    }

    @Bean
    public FilterSecurityInterceptor dynamicFilterSecurityInterceptor(DynamicSecurityMetadataSource metadataSource,
                                                                      DynamicAccessDecisionManager accessDecisionManager) {
        FilterSecurityInterceptor interceptor = new FilterSecurityInterceptor();
        interceptor.setSecurityMetadataSource(metadataSource);
        interceptor.setAccessDecisionManager(accessDecisionManager);
        return interceptor;
    }

}
