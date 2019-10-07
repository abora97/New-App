package com.example.newsapp.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.ApiCall.APIInterface;
import com.example.newsapp.ApiCall.ApiClient;
import com.example.newsapp.adapters.NewsAdapter;
import com.example.newsapp.R;
import com.example.newsapp.utils.Constants;
import com.example.newsapp.model.headLines.Article;
import com.example.newsapp.model.headLines.ResponseHeadLines;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {

    @BindView(R.id.rec_news)
    RecyclerView recNews;
    List<Article> articleList;
    private RecyclerView.LayoutManager newsLayoutManager;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);


        getNews();
    }

    void getNews() {
        final APIInterface apiService = ApiClient.getClient().create(APIInterface.class);
        Call<ResponseHeadLines> call = apiService.getNews(Constants.COUNTRY_US, Constants.KEY);
        call.enqueue(new Callback<ResponseHeadLines>() {
            @Override
            public void onResponse(Call<ResponseHeadLines> call, Response<ResponseHeadLines> response) {
                if (response.body().getStatus().equals("ok")) {
                    articleList = response.body().getArticles();
                    if (articleList.size() > 0) {
                        initRecycle();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseHeadLines> call, Throwable t) {
                Log.e("out", t.toString());
            }
        });
    }

    private void initRecycle() {

        newsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        newsAdapter = new NewsAdapter(articleList);
        recNews.setLayoutManager(newsLayoutManager);
        recNews.setAdapter(newsAdapter);
    }

    private void showFilterDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_filter);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        ImageView ivClose = dialog.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(v -> dialog.dismiss());

        Button buCancel = dialog.findViewById(R.id.bu_cancel);
        buCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
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
}
