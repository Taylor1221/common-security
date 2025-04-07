package com.taylor.common.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证相关配置属性
 *
 * @author loveCamille
 * @date 2025-04-03 09:47:46
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "common.security")
public class SecurityProperties {

    /**
     * 白名单链接
     */
    private List<String> ignoreUrls = new ArrayList<>();

}
