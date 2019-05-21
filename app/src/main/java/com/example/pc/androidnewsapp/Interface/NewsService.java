package com.example.pc.androidnewsapp.Interface;

import com.example.pc.androidnewsapp.Common.Common;
import com.example.pc.androidnewsapp.Model.News;
import com.example.pc.androidnewsapp.Model.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NewsService {
    @GET("v2/sources?language=en&apiKey="+ Common.API_KEY)
    Call<WebSite> getSources();

    @GET
    Call<News> getNewestArticles(@Url String url);
}
