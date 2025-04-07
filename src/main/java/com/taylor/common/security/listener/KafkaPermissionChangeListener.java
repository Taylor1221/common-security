package com.taylor.common.security.listener;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

import static com.taylor.common.security.constant.SecurityConstant.KafkaSecurityConstants.GROUP_ID;
import static com.taylor.common.security.constant.SecurityConstant.KafkaSecurityConstants.TOPIC;

/**
 * kafka类型权限改变监听
 *
 * @author loveCamille
 * @date 2025-04-07 14:00:23
 */
@AllArgsConstructor
public class KafkaPermissionChangeListener {

    private final PermissionLoader permissionLoader;

    @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
    public void handleKafkaMessage() {
        permissionLoader.loadPermissions();
    }

}
