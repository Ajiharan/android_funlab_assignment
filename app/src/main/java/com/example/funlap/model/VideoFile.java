package com.example.funlap.model;

import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

public class VideoFile {
    private String id;
    private String title;
    private String desc;
    private String uri;

    public VideoFile(String id, String title, String desc,String uri) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.uri=uri;
    }

    public VideoFile(String title, String desc,String uri) {
        this.title = title;
        this.desc = desc;
        this.uri=uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Map setVideo(){
        Map<String, Object> video_data = new HashMap<>();
        video_data.put("videoTitle", getTitle());
        video_data.put("videoDesc", getDesc());
        video_data.put("videoUrl", getUri());
        return video_data;
    }
}
