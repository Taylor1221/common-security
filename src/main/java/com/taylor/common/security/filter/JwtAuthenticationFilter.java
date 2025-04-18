package com.taylor.common.security.filter;

import cn.hutool.core.text.CharSequenceUtil;
import com.taylor.common.base.lock.ILock;
import com.taylor.common.jwt.manager.JwtTokenManager;
import com.taylor.common.jwt.provider.JwtProvider;
import com.taylor.common.web.constant.WebConstant;
import com.taylor.common.web.domain.HttpStatus;
import com.taylor.common.web.domain.Result;
import com.taylor.common.security.support.ResponseToolKit;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 认证过滤器
 *
 * @author loveCamille
 * @date 2025-04-02 21:17:26
 */
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtProvider jwtProvider;

    private UserDetailsService userDetailsService;

    private JwtTokenManager jwtTokenManager;

    private final ILock lock;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        // 获取 Authorization 头
        String token = request.getHeader(WebConstant.AUTH_TOKEN_HEADER);
        // 检查 JWT 令牌格式
        if (CharSequenceUtil.isEmpty(token) || !jwtProvider.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 与服务器保存的不一致
        if (!jwtTokenManager.contains(token)) {
            ResponseToolKit.sendJsonErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
                    Result.reply(HttpStatus.BaseHttpStatus.UNAUTHORIZED.getCode(),
                            "登录信息已失效，请重新登录"));
            return;
        }

        String username = jwtProvider.getUsername(token);

        // 加载用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // 设置身份信息
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 该token快要过期
        if (jwtProvider.isAboutExpired(token)) {
            lock.lock(username);
            try {
                if (token.equals(jwtTokenManager.get(username))) {
                    String newToken = jwtProvider.generateToken(username);
                    jwtTokenManager.put(username, newToken);
                    response.setHeader("Authorization", newToken);
                }
            } finally {
                lock.unlock(username);
            }
        }
        filterChain.doFilter(request, response);
    }
}
