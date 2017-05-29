package com.example.divergent.fabflixapp;

import org.json.JSONObject;

/**
 * Created by Divergent on 5/27/2017.
 */

public class MyTask {
    private String url;
    private JSONObject jsonObject;
    private String searchText = null;

    public MyTask()
    {

    }
    public void setSearchText(String text)
    {
        searchText = text;
    }
    public String getSearchText()
    {
        return searchText;
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
