package com.taylor.common.security.config;

import com.taylor.common.security.manager.DynamicAccessDecisionManager;
import com.taylor.common.security.metadata.DynamicSecurityMetadataSource;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * FilterSecurityInterceptor 后置处理器
 *
 * @author loveCamille
 * @date 2025-04-08 10:17:53
 */
@AllArgsConstructor
public class FilterSecurityInterceptorPostProcessor implements ObjectPostProcessor<FilterSecurityInterceptor> {

    private final DynamicSecurityMetadataSource securityMetadataSource;

    private final DynamicAccessDecisionManager accessDecisionManager;

    @Override
    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
        // 替换默认的元数据源和决策管理器
        object.setSecurityMetadataSource(securityMetadataSource);
        object.setAccessDecisionManager(accessDecisionManager);
        return object;
    }
}
