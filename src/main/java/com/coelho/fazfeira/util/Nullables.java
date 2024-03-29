package com.coelho.fazfeira.util;

public class Nullables {
    private Nullables(){
        throw new IllegalStateException("Utility class");
    }
    public static boolean isNullOrEmpty(String str){
        return str == null || str.isEmpty();
    }
    public static boolean isNull(Object obj){
        return obj == null;
    }
    public static boolean isNotNull(Object obj){
        return obj != null;
    }
}
