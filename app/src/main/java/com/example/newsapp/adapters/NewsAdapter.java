package com.example.newsapp.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

import com.example.newsapp.R;
import com.example.newsapp.model.headLines.Article;
import com.example.newsapp.utils.DateConverter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;




public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    List<Article> list;
   private DateConverter dateConverter=new DateConverter();

    public NewsAdapter(List<Article> list) {
        this.list = list;
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



        Date arrivalDate =dateConverter.getDateFromDepartureOrArrivalInquiryString(list.get(position).getPublishedAt());
        String arrivalDateString = dateConverter.getDateFromDate(arrivalDate);
        holder.tvDate.setText(arrivalDateString);

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
