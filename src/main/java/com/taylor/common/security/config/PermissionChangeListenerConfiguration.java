package com.taylor.common.security.config;

import com.taylor.common.security.listener.*;
import com.taylor.common.security.metadata.DynamicSecurityMetadataSource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * 权限变更自动配置
 * @author loveCamille
 * @date 2025-04-05 17:51:55
 */
@Configuration(proxyBeanMethods = false)
public class PermissionChangeListenerConfiguration {

    @Bean
    @ConditionalOnMissingBean(PermissionLoader.class)
    public PermissionLoader permissionLoader(DynamicSecurityMetadataSource dynamicSecurityMetadataSource) {
        return new DefaultPermissionLoader(dynamicSecurityMetadataSource);
    }

    @Bean
    public PermissionChangeEventListener permissionChangeEventListener(PermissionLoader permissionLoader) {
        return new PermissionChangeEventListener(permissionLoader);
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(KafkaTemplate.class)
    public static class KafkaPermissionChangeListenerConfiguration {

        @Bean
        public KafkaPermissionChangeListener kafkaPermissionChangeListener(PermissionLoader permissionLoader) {
            return new KafkaPermissionChangeListener(permissionLoader);
        }

    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(RabbitTemplate.class)
    public static class RabbitPermissionChangeListenerConfiguration {

        @Bean
        public RabbitPermissionChangeListener rabbitPermissionChangeListener(PermissionLoader permissionLoader) {
            return new RabbitPermissionChangeListener(permissionLoader);
        }

    }

}
