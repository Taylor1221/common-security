package com.taylor.common.security.listener;

import com.taylor.common.security.event.PermissionChangeEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;

/**
 * 事件类型权限改变监听
 *
 * @author loveCamille
 * @date 2025-04-07 13:59:01
 */
@AllArgsConstructor
public class PermissionChangeEventListener implements ApplicationListener<PermissionChangeEvent> {

    private final PermissionLoader permissionLoader;

    @Override
    public void onApplicationEvent(@NonNull PermissionChangeEvent event) {
        permissionLoader.loadPermissions();
    }
}
