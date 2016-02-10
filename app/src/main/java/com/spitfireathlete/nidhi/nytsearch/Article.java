package com.spitfireathlete.nidhi.nytsearch;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nidhikulkarni on 2/9/16.
 */
public class Article implements Serializable {

    private String webURL;
    private String headline;
    private String thumbnail;

    public Article(JSONObject json) {
        try {
            this.webURL = json.getString("web_url");
            this.headline = json.getJSONObject("headline").getString("main");
            JSONArray mm = json.getJSONArray("multimedia");
            if (mm.length() > 0) {
                JSONObject mmJson = mm.getJSONObject(0);
                this.thumbnail = "http://www.nytimes.com/" + mmJson.getString("url");
            } else {
                this.thumbnail = "";
            }

        } catch (JSONException e) {
            //FIXME: do something
            Log.e("ERROR", e.toString());
        }
    }

    public static List<Article> fromJSONArray(JSONArray array) {
        List<Article> articles = new ArrayList<Article>();

        for (int i = 0; i < array.length(); i++) {
            try {
                articles.add(new Article(array.getJSONObject(i)));
            } catch (JSONException e) {
                //FIXME: do something
                Log.e("ERROR", e.toString());
            }
        }

        return articles;
    }

    public String getWebURL() {
        return webURL;
    }


    public String getHeadline() {
        return headline;
    }



    public String getThumbnail() {
        return thumbnail;
    }


}
