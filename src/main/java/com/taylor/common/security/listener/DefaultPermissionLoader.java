package com.taylor.common.security.listener;

import com.taylor.common.security.metadata.DynamicSecurityMetadataSource;
import lombok.AllArgsConstructor;

/**
 * 默认权限重载器
 *
 * @author loveCamille
 * @date 2025-04-07 13:56:00
 */
@AllArgsConstructor
public class DefaultPermissionLoader implements PermissionLoader {

    private final DynamicSecurityMetadataSource metadataSource;

    @Override
    public void loadPermissions() {
        metadataSource.loadPermissions();
    }
}
