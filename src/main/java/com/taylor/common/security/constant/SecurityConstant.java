package com.taylor.common.security.constant;

/**
 * 权限相关常量
 *
 * @author loveCamille
 * @date 2025-04-07 14:18:32
 */
public class SecurityConstant {

    public static final class KafkaSecurityConstants {

        public static final String TOPIC = "permission-change-topic";

        public static final String GROUP_ID = "security-group";

    }


    public static final class RabbitmqSecurityConstants {

        public static final String QUEUE = "permission-change-queue";

    }

}
