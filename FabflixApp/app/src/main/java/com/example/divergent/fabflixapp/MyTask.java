package com.example.divergent.fabflixapp;

import org.json.JSONObject;

/**
 * Created by Divergent on 5/27/2017.
 */

public class MyTask {
    private String url;
    private JSONObject jsonObject;

    public MyTask()
    {

    }
    public void setUrl(String url)
    {
        this.url = url;
    }
    public void setJsonObject(JSONObject obj)
    {
        jsonObject = obj;
    }
    public String getUrl()
    {
        return url;
    }
    public JSONObject getJsonObject()
    {
        return jsonObject;
    }
}
