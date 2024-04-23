package com.standalone.firebasenotes.models;

import java.util.Map;
import java.util.Objects;

public abstract class BaseModel {
    String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public abstract Map<String, Object> toMap();
}
