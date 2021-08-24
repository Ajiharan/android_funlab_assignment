package com.example.funlap.model;

import java.util.HashMap;
import java.util.Map;

public class Story {
    private String title;
    private String author;
    private String desc;
    private String id;

    public Story(String title, String author, String desc, String id) {
        this.title = title;
        this.author = author;
        this.desc = desc;
        this.id = id;
    }

    public Story(String title, String author, String desc) {
        this.title = title;
        this.author = author;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public Map setStory(){
        Map<String, Object> story_data = new HashMap<>();
        story_data.put("storyTitle", getTitle());
        story_data.put("storyAuthor", getAuthor());
        story_data.put("storyDesc", getDesc());
        return story_data;
    }
}
