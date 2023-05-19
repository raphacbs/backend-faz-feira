package com.coelho.fazfeira.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public final class Converts {

    private Converts(){
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    public static String asString(UUID uuid){
        return uuid != null ? uuid.toString() : null;
    }
    public static String asString(LocalDate date){
        return date != null ? date.toString() : null;
    }
    public static String asString(LocalDateTime date){
        return date != null ? date.toString() : null;
    }



}
