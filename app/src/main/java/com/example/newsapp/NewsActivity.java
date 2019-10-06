package com.example.newsapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsActivity extends AppCompatActivity {

    @BindView(R.id.rec_news)
    RecyclerView recNews;
    List<String> list;
    private RecyclerView.LayoutManager newsLayoutManager;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        list = new ArrayList<>();

        list.add("ali");
        list.add("hana");
        list.add("sara");
        list.add("ali");

        init();
    }

    private void init() {

        newsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        newsAdapter = new NewsAdapter(list);
        recNews.setLayoutManager(newsLayoutManager);
        recNews.setAdapter(newsAdapter);
    }
}
