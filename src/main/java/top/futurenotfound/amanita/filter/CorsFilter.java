package top.futurenotfound.amanita.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CORS过滤器
 *
 * @author liuzhuoming
 */
@Slf4j
@Component
@Order(1)
@WebFilter(filterName = "CorsFilter", urlPatterns = "/*")
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", origin == null ? "*" : origin);
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Authorization,Source,AccessKey,AccessSecret");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpStatus.OK.value());
            return;
        }
        chain.doFilter(req, resp);
    }
}
