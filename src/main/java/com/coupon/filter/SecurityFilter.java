package com.coupon.filter;

import com.coupon.utils.Constants;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(urlPatterns = {"/"})
@Configuration
public class SecurityFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
//
//    private Pattern FF_DOMAIN_PATTRN = Pattern.compile(Constants.DOMAIN_PATTERN);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //支持跨域(只有以允许的域名白名单范围内的才可以跨域)
        String origin = request.getHeader("Origin");
//        if (StringUtil.isNotEmpty(origin) && FF_DOMAIN_PATTRN.matcher(origin.toLowerCase()).matches()) {

            response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            response.addHeader("Access-Control-Allow-Credentials", "true");
            // 判断是否为跨域的预检请求
            if ("OPTIONS".equals(request.getMethod())) {
                response.addHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
                response.addHeader("Access-Control-Max-Age", String.valueOf(Constants.CORS_OGIGIN_SECONDS));
                response.addHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
                response.getWriter().flush();
                return;
            }

//        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
