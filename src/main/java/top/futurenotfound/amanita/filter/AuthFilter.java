package top.futurenotfound.amanita.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import top.futurenotfound.amanita.domain.Access;
import top.futurenotfound.amanita.domain.UrlAuth;
import top.futurenotfound.amanita.domain.User;
import top.futurenotfound.amanita.env.Constant;
import top.futurenotfound.amanita.env.SourceType;
import top.futurenotfound.amanita.env.UrlAuthMethodType;
import top.futurenotfound.amanita.env.UserRoleType;
import top.futurenotfound.amanita.exception.AuthException;
import top.futurenotfound.amanita.exception.BookmarkException;
import top.futurenotfound.amanita.exception.ExceptionCode;
import top.futurenotfound.amanita.exception.GlobalExceptionCode;
import top.futurenotfound.amanita.helper.JwtHelper;
import top.futurenotfound.amanita.service.AccessService;
import top.futurenotfound.amanita.service.UserService;
import top.futurenotfound.amanita.util.CurrentLoginUser;
import top.futurenotfound.amanita.util.StringUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * 认证过滤器
 *
 * @author liuzhuoming
 * @see UrlAuth
 */
@Component
@Order(2)
@WebFilter(filterName = "AuthFilter", urlPatterns = "/*")
@RequiredArgsConstructor
@Slf4j
public class AuthFilter implements Filter {
    /**
     * 不登录就可以访问
     */
    private static final List<UrlAuth> NOT_LOGIN_ALLOW_URL_LIST = List.of(new UrlAuth(UrlAuthMethodType.GET, "/favicon.ico"), new UrlAuth(UrlAuthMethodType.GET, "/css/**"), new UrlAuth(UrlAuthMethodType.GET, "/js/**"), new UrlAuth(UrlAuthMethodType.GET, "/doc.html"), new UrlAuth(UrlAuthMethodType.GET, "/webjars/**"), new UrlAuth(UrlAuthMethodType.GET, "/swagger**"), new UrlAuth(UrlAuthMethodType.GET, "/v3/**"), new UrlAuth(UrlAuthMethodType.GET, "/token/**"), new UrlAuth(UrlAuthMethodType.GET, "/redirect/**"), new UrlAuth(UrlAuthMethodType.POST, "/user"));
    /**
     * 登录后无需权限配置就可以访问
     */
    private static final List<UrlAuth> LOGIN_ALLOW_URL_LIST = List.of(new UrlAuth(UrlAuthMethodType.ALL, "/bookmark/**"), new UrlAuth(UrlAuthMethodType.ALL, "/tag/**"), new UrlAuth(UrlAuthMethodType.ALL, "/user/**"));
    /**
     * 登录后USER及以上权限配置就可以访问
     */
    private static final List<UrlAuth> USER_ALLOW_URL_LIST = List.of();
    /**
     * 登录后VIP及以上权限配置就可以访问
     */
    private static final List<UrlAuth> VIP_ALLOW_URL_LIST = List.of(new UrlAuth(UrlAuthMethodType.ALL, "/access/**"));
    /**
     * 登录后ADMIN及以上权限配置就可以访问
     */
    private static final List<UrlAuth> ADMIN_ALLOW_URL_LIST = List.of(new UrlAuth(UrlAuthMethodType.ALL, "/member/**"));
    /**
     * 角色路由策略
     */
    private static final Map<UserRoleType, Predicate<HttpServletRequest>> ROLE_STRATEGY = Map.of(UserRoleType.USER, (HttpServletRequest request) -> matchAny(USER_ALLOW_URL_LIST, request), UserRoleType.VIP, (HttpServletRequest request) -> matchAny(USER_ALLOW_URL_LIST, request) || matchAny(VIP_ALLOW_URL_LIST, request), UserRoleType.ADMIN, (HttpServletRequest request) -> matchAny(USER_ALLOW_URL_LIST, request) || matchAny(VIP_ALLOW_URL_LIST, request) || matchAny(ADMIN_ALLOW_URL_LIST, request));

    private final UserService userService;
    private final AccessService accessService;
    private final JwtHelper jwtHelper;

    /**
     * 判断url请求是否配置在patterns列表中
     */
    private static boolean matchAny(List<UrlAuth> urlAuths, HttpServletRequest request) {
        String url = request.getRequestURI();
        String method = request.getMethod();
        final AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (UrlAuth urlAuth : urlAuths) {
            String urlpattern = urlAuth.getUrl();
            UrlAuthMethodType methodPattern = urlAuth.getMethod();
            if (url.startsWith("/")) url = url.substring(1);
            if (urlpattern.startsWith("/")) urlpattern = urlpattern.substring(1);

            if (antPathMatcher.match(urlpattern, url)) {
                UrlAuthMethodType urlAuthMethodType = UrlAuthMethodType.getByName(method);
                //匹配方式为ALL则直接返回true
                if (UrlAuthMethodType.ALL.equals(methodPattern)) return true;
                return methodPattern.equals(urlAuthMethodType);
            }
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (matchAny(NOT_LOGIN_ALLOW_URL_LIST, req)) {
            chain.doFilter(req, resp);
            return;
        }

        //除静态资源和获取token接口之外其他请求必须携带请求来源标识source
        String source = req.getHeader(Constant.HEADER_SOURCE);
        if (StringUtil.isEmpty(source)) {
            sendError(resp, GlobalExceptionCode.SOURCE_IS_REQUIRED);
            return;
        }

        SourceType sourceType = SourceType.getByName(source);
        if (sourceType == null) {
            sendError(resp, GlobalExceptionCode.UNKNOWN_SOURCE);
            return;
        }

        //当前认证用户（待认证初始化
        User user;
        try {
            switch (sourceType) {
                case WEB: //jwt验证
                    String token = req.getHeader(Constant.HEADER_AUTHORIZATION);
                    if (StringUtil.isEmpty(token)) {
                        sendError(resp, GlobalExceptionCode.TOKEN_EXPIRED);
                        return;
                    }
                    try {
                        String username = jwtHelper.getUsername(token);
                        user = userService.getByUsername(username);
                    } catch (Exception e) {
                        sendError(resp, GlobalExceptionCode.TOKEN_EXPIRED);
                        return;
                    }
                    break;
                case API: //access验证
                    String accessKey = req.getHeader(Constant.HEADER_ACCESS_KEY);
                    String accessSecret = req.getHeader(Constant.HEADER_ACCESS_SECRET);
                    Access access = accessService.getByKeyAndSecret(accessKey, accessSecret);
                    if (access == null) {
                        sendError(resp, GlobalExceptionCode.ACCESS_EXPIRED);
                        return;
                    }
                    user = userService.getById(access.getUserId());
                    //只有VIP及ADMIN具备access认证权限
                    if (!Objects.equals(user.getRole(), UserRoleType.VIP.getName()) && !Objects.equals(user.getRole(), UserRoleType.ADMIN.getName())) {
                        sendError(resp, GlobalExceptionCode.NO_AUTH);
                        return;
                    }
                    break;
                default:
                    sendError(resp, GlobalExceptionCode.UNKNOWN_SOURCE);
                    return;
            }

            if (user == null) {
                sendError(resp, GlobalExceptionCode.USER_NOT_EXIST);
                return;
            }
            //存入线程变量
            CurrentLoginUser.set(user);
        } catch (AuthException | BookmarkException e) {
            sendError(resp, e.getExceptionCode());
            return;
        } catch (Exception e) {
            sendError(resp, GlobalExceptionCode.FAIL);
            return;
        }

        if (matchAny(LOGIN_ALLOW_URL_LIST, req)) {
            chain.doFilter(req, resp);
            return;
        }

        UserRoleType userRoleType = UserRoleType.getByName(user.getRole());
        //针对角色的路由策略模式
        if (ROLE_STRATEGY.get(userRoleType).test(req)) {
            chain.doFilter(req, resp);
            return;
        }

        //不符合任意一种匹配规则则视为无效请求
        sendError(resp, GlobalExceptionCode.NO_ROUTE_ERROR);
    }

    private void sendError(HttpServletResponse resp, ExceptionCode exceptionCode) throws IOException {
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.getWriter().print(exceptionCode);
    }
}

