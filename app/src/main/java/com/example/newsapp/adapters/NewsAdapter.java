package com.example.newsapp.adapters;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;


import java.util.Date;
import java.util.List;

import com.example.newsapp.R;
import com.example.newsapp.activity.DetailsActivity;
import com.example.newsapp.model.headLines.Article;
import com.example.newsapp.utils.Constants;
import com.example.newsapp.utils.DateConverter;
import com.squareup.picasso.Picasso;


import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<Article> list;
    private Context context;
    private DateConverter dateConverter = new DateConverter();

    public NewsAdapter(List<Article> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvHeadLine.setText(list.get(position).getTitle());
        Date arrivalDate = dateConverter.getDateFromDepartureOrArrivalInquiryString(list.get(position).getPublishedAt());
        String arrivalDateString = dateConverter.getDateFromDate(arrivalDate);
        holder.tvDate.setText(arrivalDateString);
        Picasso.get()
                .load(list.get(position).getUrlToImage())
                .placeholder(R.drawable.icon_news)
                .error(R.drawable.icon_news)
                .into(holder.ivNews);

        holder.linearLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.DETAILS_DATA,list.get(position));
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_news)
        ImageView ivNews;
        @BindView(R.id.tv_head_line)
        TextView tvHeadLine;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.lay_news)
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
