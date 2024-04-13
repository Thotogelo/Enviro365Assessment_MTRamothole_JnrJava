package com.enviro.assessment.grad001.thotogeloramothole.Logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoggingInterceptor implements HandlerInterceptor {
//    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        logger.info("Request URL: " + request.getRequestURL().toString() +
//                ", Method : " + request.getMethod() +
//                ", IP : " + request.getRemoteAddr());
//        return true;
//    }
}
