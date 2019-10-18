package com.example.newsapp.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.newsapp.ApiCall.APIInterface;
import com.example.newsapp.ApiCall.ApiClient;
import com.example.newsapp.R;
import com.example.newsapp.adapters.NewsAdapter;
import com.example.newsapp.model.headLines.Article;
import com.example.newsapp.model.headLines.ResponseHeadLines;
import com.example.newsapp.utils.Constants;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rec_news)
    RecyclerView recNews;
    List<Article> articleList;
    @BindView(R.id.progress_news)
    ProgressBar progressNews;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    private RecyclerView.LayoutManager newsLayoutManager;
    private NewsAdapter newsAdapter;

    int isNews;
    String country = "us", sources = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        swipeLayout.setOnRefreshListener(this);
        getNews();
    }

    // api call to get all news by country
    void getNews() {
        isNews = 1;
        progressNews.setVisibility(View.VISIBLE);
        final APIInterface apiService = ApiClient.getClient().create(APIInterface.class);
        Call<ResponseHeadLines> call = apiService.getNews(country, Constants.KEY);
        callEnqueue(call);
    }

    //api call to get news ny specific source
    void getNewsWithSource() {
        isNews = 0;
        progressNews.setVisibility(View.VISIBLE);
        final APIInterface apiService = ApiClient.getClient().create(APIInterface.class);
        Call<ResponseHeadLines> call = apiService.getNewsWithSource(sources, Constants.KEY);
        callEnqueue(call);
    }

    private void callEnqueue(Call<ResponseHeadLines> call) {
        call.enqueue(new Callback<ResponseHeadLines>() {
            @Override
            public void onResponse(Call<ResponseHeadLines> call, Response<ResponseHeadLines> response) {
                if (response.body().getStatus().equals("ok")) {
                    articleList = response.body().getArticles();
                    if (articleList.size() > 0) {
                        initRecycle();
                        swipeLayout.setRefreshing(false);
                        progressNews.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseHeadLines> call, Throwable t) {
                Log.e("out", t.toString());
                progressNews.setVisibility(View.GONE);
            }
        });
    }

    // this method to set data in recycleView
    // 1-setLayoutManager
    // 2-setAdapter
    private void initRecycle() {
        newsAdapter = new NewsAdapter(articleList, NewsActivity.this);
        recNews.setLayoutManager(getLayoutManager());
        recNews.setAdapter(newsAdapter);
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        if (newsLayoutManager == null) {
            newsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        }
        return newsLayoutManager;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_filter:
                showFilterDialog();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // this method to show filterDialog and handle all action
    private void showFilterDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_filter);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        ImageView ivClose = dialog.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(v -> dialog.dismiss());
        Button buCancel = dialog.findViewById(R.id.bu_cancel);
        //btn
        buCancel.setOnClickListener(v -> dialog.dismiss());
        Spinner spinnerCountry = dialog.findViewById(R.id.spinner_country);
        Spinner spinnerSources = dialog.findViewById(R.id.spinner_sources);
        Button buFilter = dialog.findViewById(R.id.bu_filter);
        buFilter.setOnClickListener(v -> {
            country = spinnerCountry.getSelectedItem().toString();
            sources = spinnerSources.getSelectedItem().toString();
            if (!country.equals("Select Country")) {
                getNews();
                dialog.dismiss();
            } else if (!sources.equals("Select News Sources")) {
                getNewsWithSource();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onRefresh() {
        if (isNews == 0) {
            getNewsWithSource();
        } else {
            getNews();
        }
    }
}
