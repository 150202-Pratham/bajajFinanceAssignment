package com.bajaj.api.Bajaj.API.keys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bajaj.api.Bajaj.API.keys.dto.ApiResponse;
import com.bajaj.api.Bajaj.API.keys.service.AIService;
import com.bajaj.api.Bajaj.API.keys.service.MathService;
import com.bajaj.api.Bajaj.API.keys.util.Validator;
import java.util.*;

@RestController
@RequestMapping("/")
public class bfhlcontroller {
    @Autowired 
    MathService math;
    @Autowired 
    AIService ai;
    @Autowired 
    Validator validator;
    
    private final String EMAIL = "aryan0133.be23@chitkara.edu.in";
    
    @PostMapping("bfhl")
    public ApiResponse process(@RequestBody Map<String,Object> req) throws Exception {
        validator.validate(req);
        String key = req.keySet().iterator().next();
        Object val = req.get(key);
        Object result;
        
        switch(key) {
            case "fibonacci":
                result = math.fibonacci((Integer)val);
                break;
            case "prime":
                result = math.prime(convertToIntList((List<?>)val));
                break;
            case "lcm":
                result = math.lcm(convertToIntList((List<?>)val));
                break;
            case "hcf":
                result = math.hcf(convertToIntList((List<?>)val));
                break;
            case "AI":
                result = ai.askAI((String)val);
                break;
            default:
                throw new IllegalArgumentException("Invalid key");
        }
        
        return new ApiResponse(true, EMAIL, result);
    }

    @GetMapping("health")
    public ApiResponse health() {
        return new ApiResponse(true, EMAIL, null);
    }
    
    private List<Integer> convertToIntList(List<?> list) {
        return list.stream()
            .map(x -> {
                if (x instanceof Integer) return (Integer) x;
                if (x instanceof Double) return ((Double) x).intValue();
                if (x instanceof Long) return ((Long) x).intValue();
                throw new IllegalArgumentException("Invalid number in array");
            })
            .toList();
    }
}