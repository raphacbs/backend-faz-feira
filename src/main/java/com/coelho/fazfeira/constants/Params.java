package com.coelho.fazfeira.constants;

import java.util.HashMap;
import java.util.Map;

public class Params {
    private Params() {
        throw new IllegalStateException("Utility class");
    }

    public static final String UNIT_DESCRIPTION = "description";
    public static final String UNIT_INITIALS = "initials";
    public static final String ID = "id";
    public static final String NO_PAGE = "pageNo";
    public static final String PAGE_SIZE = "pageSize";
    public static final String SORT_BY = "sortBy";
    public static final String SORT_DIR = "sortDir";

    public static Map<String, Object> getDefaultParams() {
        Map<String, Object> params = new HashMap<>();
        params.put(PAGE_SIZE, 10);
        params.put(NO_PAGE, 1);
        params.put(SORT_BY, "description");
        params.put(SORT_DIR, "asc");
        return params;
    }
}
