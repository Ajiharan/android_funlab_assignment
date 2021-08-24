package com.example.funlap.model;

import java.util.HashMap;
import java.util.Map;

public class Fun {
    private String id;
    private String title;
    private String category;
    private String desc;

    public Fun(String id, String title, String category, String desc) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.desc = desc;
    }

    public Fun(String title, String category, String desc) {
        this.title = title;
        this.category = category;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public Map setFun(){
        Map<String, Object> fun_data = new HashMap<>();
        fun_data.put("funTitle", getTitle());
        fun_data.put("funCategory", getCategory());
        fun_data.put("funDescription", getDesc());
        return fun_data;
    }
}
