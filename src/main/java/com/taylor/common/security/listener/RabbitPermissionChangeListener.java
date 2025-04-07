package com.taylor.common.security.listener;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import static com.taylor.common.security.constant.SecurityConstant.RabbitmqSecurityConstants.QUEUE;

/**
 * rabbitmq权限改变监听
 *
 * @author loveCamille
 * @date 2025-04-07 14:01:24
 */
@AllArgsConstructor
public class RabbitPermissionChangeListener {

    private final PermissionLoader permissionLoader;

    @RabbitListener(queues = QUEUE)
    public void handleRabbitMessage() {
        permissionLoader.loadPermissions();
    }

}
