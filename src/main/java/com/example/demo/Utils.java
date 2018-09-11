package com.example.demo;

public class Utils {

    public static String getMethodNameFromUtils() {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        return methodName;
    }
}
