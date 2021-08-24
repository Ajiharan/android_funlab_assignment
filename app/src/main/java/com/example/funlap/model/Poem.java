package com.example.funlap.model;

import java.util.HashMap;
import java.util.Map;

public class Poem {
    private String title;
    private String poemText;
    private String desc;
    private String id;

    public Poem(String title, String poemText, String desc, String id) {
        this.title = title;
        this.poemText = poemText;
        this.desc = desc;
        this.id = id;
    }

    public Poem(String title, String poemText, String desc) {
        this.title = title;
        this.poemText = poemText;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoemText() {
        return poemText;
    }

    public void setPoemText(String poemText) {
        this.poemText = poemText;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map setPoem(){
        Map<String, Object> poem_data = new HashMap<>();
        poem_data.put("poemTitle", getTitle());
        poem_data.put("poemDescription", getDesc());
        poem_data.put("poem", getDesc());
        return poem_data;
    }
}
