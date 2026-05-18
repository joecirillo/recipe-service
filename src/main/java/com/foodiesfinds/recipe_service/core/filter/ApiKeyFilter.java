package com.foodiesfinds.recipe_service.core.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodiesfinds.recipe_service.core.config.ApiKeyProperties;
import com.foodiesfinds.recipe_service.dto.core.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

    private final ApiKeyProperties apiKeyProperties;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String apiKey = request.getHeader("X-Api-Key");
        boolean isAdminEndpoint = request.getMethod().equalsIgnoreCase("DELETE")
                && request.getRequestURI().startsWith("/recipe/delete/");

        if (apiKey != null) {
            String adminKey = apiKeyProperties.getKeys().get("admin");
            if (isAdminEndpoint && apiKey.equals(adminKey)) {
                filterChain.doFilter(request, response);
                return;
            } else if (!isAdminEndpoint && apiKeyProperties.getKeys().containsValue(apiKey)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.UNAUTHORIZED)
                .message("Invalid or missing API key")
                .path(request.getRequestURI())
                .build();

        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
