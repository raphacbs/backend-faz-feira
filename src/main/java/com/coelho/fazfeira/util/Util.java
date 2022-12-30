package com.coelho.fazfeira.util;

import org.postgresql.util.PSQLException;

public class Util {

    private Util(){
        throw new IllegalStateException("Utility class");
    }

    public static boolean isCausedBy(Throwable caught, Class<? extends Throwable> isOfOrCausedBy) {
        if (caught == null) return false;
        else if (isOfOrCausedBy.isAssignableFrom(caught.getClass())) return true;
        else return isCausedBy(caught.getCause(), isOfOrCausedBy);
    }

    public static PSQLException  getCausePSQLException(Throwable caught) {
        if (caught == null) return null;
        else if ( PSQLException.class.isAssignableFrom(caught.getClass())) return (PSQLException) caught;
        else return getCausePSQLException(caught.getCause());
    }
}
