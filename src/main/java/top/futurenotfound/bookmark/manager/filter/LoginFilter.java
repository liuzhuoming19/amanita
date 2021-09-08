package top.futurenotfound.bookmark.manager.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import top.futurenotfound.bookmark.manager.entity.User;
import top.futurenotfound.bookmark.manager.helper.JwtHelper;
import top.futurenotfound.bookmark.manager.service.UserService;
import top.futurenotfound.bookmark.manager.util.CurrentLoginUser;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 登录过滤器
 *
 * @author liuzhuoming
 */
@Component
@Order(2)
@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
@RequiredArgsConstructor
public class LoginFilter implements Filter {
    /**
     * 不登录就可以访问
     */
    private static final List<String> NOT_LOGIN_URL = List.of(
            //static
            "/favicon.ico",
            "/css/**",
            "/js/**",
            //swagger
            "/doc.html",
            "/webjars/**",
            "/swagger**",
            "/v3/**",
            //other
            //登录
            "/login/**"
    );
    /**
     * 登录后无需权限配置就可以访问
     */
    private static final List<String> LOGIN_URL = List.of(

    );
    /**
     * 登录后USER权限配置就可以访问
     */
    private static final List<String> USER_URL = List.of(
            "/bookmark/**",
            "/user/**",
            "/tag/**"
    );
    /**
     * 登录后VIP权限配置就可以访问
     */
    private static final List<String> VIP_URL = List.of(
            "/access/**"
    );
    private final UserService userService;
    private final JwtHelper jwtHelper;

    /**
     * 判断url请求是否配置在patterns列表中
     */
    private static boolean matchAny(List<String> patterns, String url) {
        final AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String pattern : patterns) {
            if (url.startsWith("/")) url = url.substring(1);
            if (pattern.startsWith("/")) pattern = pattern.substring(1);
            if (antPathMatcher.match(pattern, url)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String url = req.getRequestURI();

        if (matchAny(NOT_LOGIN_URL, url)) {
            chain.doFilter(req, resp);
            return;
        }

        String token = req.getHeader("Authorization");
        //jwt解析
        try {
            String username = jwtHelper.getUsername(token);
            User user = userService.getByUsername(username);
            //存入线程变量
            CurrentLoginUser.set(user);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        if (matchAny(LOGIN_URL, url)) {
            chain.doFilter(req, resp);
            return;
        }

        chain.doFilter(request, response);
    }
}

