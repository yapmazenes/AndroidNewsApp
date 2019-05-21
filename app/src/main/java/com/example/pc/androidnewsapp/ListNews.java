package com.example.pc.androidnewsapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pc.androidnewsapp.Adapter.ListNewsAdapter;
import com.example.pc.androidnewsapp.Common.Common;
import com.example.pc.androidnewsapp.Interface.NewsService;
import com.example.pc.androidnewsapp.Model.Article;
import com.example.pc.androidnewsapp.Model.News;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListNews extends AppCompatActivity {

    KenBurnsView kbv;
    DiagonalLayout diagonalLayout;
    AlertDialog dialog;
    NewsService mService;
    TextView top_author, top_title;
    SwipeRefreshLayout swipeRefreshLayout;

    String source = "", webHotUrl;

    ListNewsAdapter adapter;
    RecyclerView lstNews;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);

        //Service
        mService = Common.getNewsService();
        dialog = new SpotsDialog.Builder().setContext(getApplicationContext()).build();
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews(source, true);
            }
        });
        diagonalLayout = findViewById(R.id.diagonalLayout);
        diagonalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Click to hot / latest news to read
                Intent detail = new Intent(getBaseContext(), DetailActivity.class);
                detail.putExtra("webURL", webHotUrl);
                startActivity(detail);
            }
        });
        kbv = findViewById(R.id.top_image);
        top_author = findViewById(R.id.top_author);
        top_title = findViewById(R.id.top_title);

        lstNews = findViewById(R.id.lstNews);
        lstNews.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        lstNews.setLayoutManager(layoutManager);


        //Intent

        if (getIntent() != null) {
            source = getIntent().getStringExtra("source");

            if (!source.isEmpty() ) {
                loadNews(source, false);
            }
        }

        //21.02
    }

    private void loadNews(String source, boolean isRefreshed) {
        if (!isRefreshed) {
            dialog.show();
            mService.getNewestArticles(Common.getAPIUrl(source, Common.API_KEY)).enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    dialog.dismiss();
                    //Get first article
                    Picasso.with(getBaseContext()).load(response.body().getArticles().get(0).getUrlToImage());
                    top_title.setText(response.body().getArticles().get(0).getTitle());
                    top_author.setText(response.body().getArticles().get(0).getAuthor());

                    webHotUrl = response.body().getArticles().get(0).getUrl();
                    //Load remain articles
                    List<Article> removeFirstItem =response.body().getArticles();
                    removeFirstItem.remove(0);
                    adapter=new ListNewsAdapter(removeFirstItem,getBaseContext());
                    adapter.notifyDataSetChanged();
                    lstNews.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {

                }
            });
        }
        else
        {
            dialog.show();
            mService.getNewestArticles(Common.getAPIUrl(source, Common.API_KEY)).enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    dialog.dismiss();
                    //Get first article
                    Picasso.with(getBaseContext()).load(response.body().getArticles().get(0).getUrlToImage());
                    top_title.setText(response.body().getArticles().get(0).getTitle());
                    top_author.setText(response.body().getArticles().get(0).getAuthor());

                    webHotUrl = response.body().getArticles().get(0).getUrl();
                    //Load remain articles
                    List<Article> removeFirstItem =response.body().getArticles();
                    removeFirstItem.remove(0);
                    adapter=new ListNewsAdapter(removeFirstItem,getBaseContext());
                    adapter.notifyDataSetChanged();
                    lstNews.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {

                }
            });
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
