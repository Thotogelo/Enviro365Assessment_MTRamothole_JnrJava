package com.enviro.assessment.grad001.thotogeloramothole.logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoggingInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    //    Intercept and Log request
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info("Received request: {} {} from {}", request.getMethod(), request.getRequestURI(),
                request.getRemoteAddr());
        return true;
    }

    //    Intercept and Log responses
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.info("Sent response: {} {} with status {} and exception {}", request.getMethod(),
                request.getRequestURI(), response.getStatus(), ex);
    }
}
