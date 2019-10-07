package com.example.newsapp.activity;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.Toast;

import com.example.newsapp.R;
import com.example.newsapp.model.headLines.Article;
import com.example.newsapp.utils.Constants;



public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        Bundle bundle = getIntent().getExtras();
        Article article = bundle.getParcelable(Constants.DETAILS_DATA);

        Toast.makeText(this, "" + article.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
