package com.example.newsapp.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsapp.R;
import com.example.newsapp.model.headLines.Article;
import com.example.newsapp.utils.Constants;
import com.example.newsapp.utils.DateConverter;
import com.squareup.picasso.Picasso;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.iv_details)
    ImageView ivDetails;
    @BindView(R.id.tv_details_head_line)
    TextView tvDetailsHeadLine;
    @BindView(R.id.tv_author_name)
    TextView tvAuthorName;
    @BindView(R.id.tv_details_date)
    TextView tvDetailsDate;
    @BindView(R.id.tv_details_description)
    TextView tvDetailsDescription;
    @BindView(R.id.tv_details_content)
    TextView tvDetailsContent;

    Article article;
    private DateConverter dateConverter = new DateConverter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            article = bundle.getParcelable(Constants.DETAILS_DATA);
            setTitle(article.getSource().getName());
            init();
        }
    }

    private void init() {
        Picasso.get()
                .load(article.getUrlToImage())
                .placeholder(R.drawable.icon_news)
                .error(R.drawable.icon_news)
                .into(ivDetails);
        tvDetailsHeadLine.setText(article.getTitle());
        Date publishedDate = dateConverter.getDateFromDepartureOrArrivalInquiryString(article.getPublishedAt());
        String publishedDateString = dateConverter.getDateFromDate(publishedDate);
        tvDetailsDate.setText(publishedDateString);
        tvDetailsDescription.setText(article.getDescription());
        tvDetailsContent.setText(Html.fromHtml(article.getContent()));
        tvAuthorName.setText(article.getAuthor());
    }
}
