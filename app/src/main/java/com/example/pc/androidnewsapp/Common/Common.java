package com.example.pc.androidnewsapp.Common;

import com.example.pc.androidnewsapp.Interface.IconBetterIdeaService;
import com.example.pc.androidnewsapp.Interface.NewsService;
import com.example.pc.androidnewsapp.Remote.IconBetterIdeaClient;
import com.example.pc.androidnewsapp.Remote.RetrofitClient;

public class Common {
    private static final String BASE_URL = "https://newsapi.org/";
    public static final String API_KEY="Your API_KEY";
    public static NewsService getNewsService() {
        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }
    public static IconBetterIdeaService getIconService() {
        return IconBetterIdeaClient.getClient().create(IconBetterIdeaService.class);
    }

    //https://newsapi.org/v1/articles?source=the-verge&apiKey=31ec226ce7d94d72b9a56818651a7bd6
    public static String getAPIUrl(String source,String apiKEY)
    {
        StringBuilder apiUrl=new StringBuilder("https://newsapi.org/v2/top-headlines?sources=");
        return apiUrl.append(source).append("&apiKey=").append(apiKEY).toString();
    }


}
