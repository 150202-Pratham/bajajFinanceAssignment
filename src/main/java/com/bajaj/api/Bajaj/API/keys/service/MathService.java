package com.bajaj.api.Bajaj.API.keys.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MathService {
    public List<Integer> fibonacci(int n) {
        if (n < 0 || n > 100) {
            throw new IllegalArgumentException("Fibonacci n must be between 0 and 100");
        }
        List<Integer> ans = new ArrayList<>();
        int a = 0, b = 1;
        for (int i = 0; i < n; i++) {
            ans.add(a);
            int c = a + b;
            a = b;
            b = c;
        }
        return ans;
    }
    public List<Integer> prime(List<Integer> nums) {
        if (nums == null || nums.isEmpty() || nums.size() > 100) {
            throw new IllegalArgumentException("Prime array must not be empty and size <= 100");
        }
        return nums.stream().filter(this::isPrime).toList();
    }
    private boolean isPrime(int n){
        if(n<2) return false;
        for(int i=2;i*i<=n;i++)
            if(n%i==0) return false;
        return true;
    }
    public int gcd(int a,int b){
        return b==0?a:gcd(b,a%b);
    }
    public int hcf(List<Integer> arr) {
        if (arr == null || arr.isEmpty() || arr.size() > 100) {
            throw new IllegalArgumentException("HCF array must not be empty and size <= 100");
        }
        if (arr.stream().anyMatch(x -> x <= 0)) {
            throw new IllegalArgumentException("HCF values must be positive integers");
        }
        int ans = arr.get(0);
        for (int x : arr) ans = gcd(ans, x);
        return ans;
    }
    public int lcm(List<Integer> arr) {
        if (arr == null || arr.isEmpty() || arr.size() > 100) {
            throw new IllegalArgumentException("LCM array must not be empty and size <= 100");
        }
        if (arr.stream().anyMatch(x -> x <= 0)) {
            throw new IllegalArgumentException("LCM values must be positive integers");
        }
        long ans = arr.get(0);
        for (int x : arr) {
            long g = gcd((int) ans, x);
            ans = ans / g * x;
            if (ans > Integer.MAX_VALUE) {
                throw new IllegalArgumentException("LCM result exceeds maximum integer value");
            }
        }
        return (int) ans;
    }
}