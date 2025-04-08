package com.taylor.common.security.white;

import lombok.AllArgsConstructor;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * 校验
 *
 * @author loveCamille
 * @date 2025-04-08 11:13:59
 */
@AllArgsConstructor
public class AntPathWhiteListChecker implements WhiteListChecker {

    private final SecurityProperties securityProperties;

    @Override
    public boolean isWhiteListed(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return securityProperties.getIgnoreUrls().stream()
                .anyMatch(pattern -> new AntPathMatcher().match(pattern, uri));
    }
}
