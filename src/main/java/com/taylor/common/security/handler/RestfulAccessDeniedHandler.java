package com.taylor.common.security.handler;

import com.taylor.common.web.domain.HttpStatus;
import com.taylor.common.web.domain.Result;
import com.taylor.common.security.support.ResponseToolKit;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限拒绝处理
 *
 * @author loveCamille
 * @date 2025-04-03 10:10:31
 */
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        ResponseToolKit.sendJsonErrorResponse(response, HttpServletResponse.SC_FORBIDDEN,
                Result.reply(HttpStatus.BaseHttpStatus.FORBIDDEN));
    }
}
