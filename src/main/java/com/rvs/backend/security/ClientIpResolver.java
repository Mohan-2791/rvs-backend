package com.rvs.backend.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClientIpResolver {

    @Value("${app.security.trust-proxy:false}")
    private boolean trustProxy;

    public String resolve(HttpServletRequest request) {
        if (trustProxy) {
            String forwarded = request.getHeader("X-Forwarded-For");
            if (forwarded != null && !forwarded.isBlank()) {
                String firstHop = forwarded.split(",")[0].trim();
                if (!firstHop.isEmpty()) {
                    return firstHop;
                }
            }
        }
        String remote = request.getRemoteAddr();
        return remote != null ? remote : "unknown";
    }
}
