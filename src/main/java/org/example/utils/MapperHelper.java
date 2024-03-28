package org.example.utils;

import java.util.UUID;

public class MapperHelper {

    public static UUID asUuid(Object object) {
        if(object != null) return UUID.fromString((String) object);
        return null;
    }

    public static Long asLong(Object object) {
        if(object != null) return Long.valueOf((Integer) object);
        return null;
    }
}
