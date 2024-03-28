package org.example.utils;

public class MapperHelper {

    public static String asString(Object object) {
        if(object != null) return (String) object;
        return null;
    }

    public static Integer asInteger(Object object) {
        if(object != null) return (Integer) object;
        return null;
    }
}
