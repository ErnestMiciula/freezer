package com.example.freezer.filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class ApiKeyRequestFilter extends GenericFilterBean {

    private final static String API_KEY = "secretkey";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI();

        if(!path.startsWith("/food")){
            chain.doFilter(request, response);
            return;
        }

        String key = req.getHeader("Key") == null ? "" : req.getHeader("Key");
        log.info("Trying key: " + key);

        if(key.equals(API_KEY)){
            chain.doFilter(request, response);
        }else{
            HttpServletResponse resp = (HttpServletResponse) response;
            String error = "Invalid API KEY";

            resp.reset();
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentLength(error .length());
            response.getWriter().write(error);
        }
    }

}