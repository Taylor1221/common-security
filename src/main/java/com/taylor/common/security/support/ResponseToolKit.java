package com.taylor.common.security.support;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 响应返回工具
 *
 * @author loveCamille
 * @date 2025-04-03 16:52:04
 */
public class ResponseToolKit {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private ResponseToolKit() {}

    public static void sendJsonErrorResponse(HttpServletResponse response, int sc,
                                             Object value) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(sc);
        response.getWriter().write(OBJECT_MAPPER.writeValueAsString(value));
    }

}
