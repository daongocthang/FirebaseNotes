package com.standalone.firebasenotes.models;

import java.util.HashMap;
import java.util.Map;

public class Note extends BaseModel {
    String title, content;

    public Note() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("content", content);

        return map;
    }
}
