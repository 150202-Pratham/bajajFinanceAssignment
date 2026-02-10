package com.bajaj.api.Bajaj.API.keys.util;

import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class Validator {
    public void validate(Map<String,Object> req) {
        if (req == null || req.size() != 1) {
            throw new IllegalArgumentException("Request must contain exactly one key");
        }
        
        String key = req.keySet().iterator().next();
        Object value = req.get(key);
        
        if (!isValidKey(key)) {
            throw new IllegalArgumentException("Invalid key. Allowed: fibonacci, prime, lcm, hcf, AI");
        }
        
        switch(key) {
            case "fibonacci":
                if (!(value instanceof Integer)) {
                    throw new IllegalArgumentException("fibonacci value must be an integer");
                }
                int n = (Integer) value;
                if (n < 0 || n > 100) {
                    throw new IllegalArgumentException("fibonacci n must be between 0 and 100");
                }
                break;
            case "prime":
            case "lcm":
            case "hcf":
                if (!(value instanceof java.util.List)) {
                    throw new IllegalArgumentException(key + " value must be an array");
                }
                java.util.List<?> list = (java.util.List<?>) value;
                if (list.isEmpty() || list.size() > 100) {
                    throw new IllegalArgumentException(key + " array must not be empty and size <= 100");
                }
                for (Object item : list) {
                    if (!(item instanceof Integer) && !(item instanceof Double)) {
                        throw new IllegalArgumentException(key + " array must contain only numbers");
                    }
                }
                break;
            case "AI":
                if (!(value instanceof String)) {
                    throw new IllegalArgumentException("AI value must be a string");
                }
                String question = (String) value;
                if (question.isBlank() || question.length() > 500) {
                    throw new IllegalArgumentException("AI question must be non-empty and <= 500 characters");
                }
                break;
        }
    }
    
    private boolean isValidKey(String key) {
        return key.equals("fibonacci") || key.equals("prime") || key.equals("lcm") || 
               key.equals("hcf") || key.equals("AI");
    }
}