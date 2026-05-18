package com.foodiesfinds.recipe_service.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "api")
public class ApiKeyProperties {
    private Map<String, String> keys;
}
