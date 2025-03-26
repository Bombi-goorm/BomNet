package com.bombi.core.application.logging;

import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getRequestURI().startsWith("/core")) {
            String requestId = UUID.randomUUID().toString();
            long startTime = System.nanoTime();

            MDC.put("TRANSFER_REQUEST", "Y");
            MDC.put("REQUEST_ID", requestId);
            MDC.put("START_TIME", String.valueOf(startTime));
            MDC.put("REQUEST_URI", request.getRequestURI());
            MDC.put("METHOD", request.getMethod());

            if(handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                String controllerName = handlerMethod.getBeanType().getSimpleName();
                String methodName = handlerMethod.getMethod().getName();

                String handlerInformation = controllerName + "." + methodName;
                MDC.put("HANDLER_INFO", handlerInformation);
            } else {
                MDC.put("HANDLER_INFO", handler.toString());
            }

            log.info("Request Start: {}", request.getRequestURI());
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            String startTimeStr = MDC.get("START_TIME");

            if (startTimeStr != null) {
                long startTime = Long.parseLong(startTimeStr);
                long endTime = System.nanoTime();
                long duration = endTime - startTime;
                double durationMs = duration / 1_000_000.0; // 밀리초로 변환

                log.info("Request completed in {} ms", String.format("%.2f", durationMs));
            } else {
                log.warn("No start time found in MDC");
            }
        } catch (NumberFormatException e) {
            log.error("Invalid start time format", e);
        } finally {
            MDC.clear();
        }
    }
}