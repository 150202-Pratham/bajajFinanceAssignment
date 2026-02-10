package com.bajaj.api.Bajaj.API.keys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private boolean is_success;
    private String official_email;
    private Object data;
    private String message;

    public ApiResponse(boolean is_success, String official_email, Object data) {
        this.is_success = is_success;
        this.official_email = official_email;
        this.data = data;
        this.message = null;
    }
}