package com.taylor.common.security.white;

import javax.servlet.http.HttpServletRequest;

public interface WhiteListChecker {

    boolean isWhiteListed(HttpServletRequest request);

}
