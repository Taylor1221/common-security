package com.taylor.common.security.event;

import org.springframework.context.ApplicationEvent;

/**
 * 权限变更事件
 *
 * @author loveCamille
 * @date 2025-04-05 16:31:33
 */
public class PermissionChangeEvent extends ApplicationEvent {

    public PermissionChangeEvent(Object source) {
        super(source);
    }

}
