package com.taylor.common.security.metadata;

import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * 动态权限控制过滤器
 */
public interface DynamicSecurityMetadataSource extends FilterInvocationSecurityMetadataSource {

    /**
     * 加载权限并更新权限数据
     * @author loveCamille
     */
    void loadPermissions();

}
