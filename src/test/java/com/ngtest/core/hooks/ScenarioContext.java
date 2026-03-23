package com.ngtest.core.hooks;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    private final Map<String, Object> values = new HashMap<>();

    public void set(String key, Object value) {
        values.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) values.get(key);
    }

    public void clear() {
        values.clear();
    }
}
