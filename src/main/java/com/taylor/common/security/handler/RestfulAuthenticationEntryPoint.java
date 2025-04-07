package com.taylor.common.security.handler;

import com.taylor.common.web.domain.HttpStatus;
import com.taylor.common.web.domain.Result;
import com.taylor.common.security.support.ResponseToolKit;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理未经认证的请求
 *
 * @author loveCamille
 * @date 2025-04-03 11:02:32
 */
public class RestfulAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ResponseToolKit.sendJsonErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                Result.reply(HttpStatus.BaseHttpStatus.UNAUTHORIZED));
    }
}
