package com.sychen.search.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sychen.search.R;
import com.sychen.search.activity.SearchMainActivity;
import com.sychen.search.network.model.SearchNews;

public class SearchAdapter extends ListAdapter<SearchNews, SearchAdapter.SearchViewHolder> {

    public SearchAdapter(@NonNull DiffUtil.ItemCallback<SearchNews> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        SearchNews news = getItem(position);
        holder.title.setText(news.getNewTitle());
        holder.author.setText(news.getAuthor());
        holder.date.setText(news.getDate());
        Glide.with(holder.itemView.getContext())
                .load(news.getNewTitleImgUrl())
                .into(holder.img);
        Intent intent = new Intent();
        intent.putExtra("Title",news.getNewTitle());
        intent.putExtra("Content",news.getContent());
        intent.putExtra("Picture",news.getNewTitleImgUrl());
        intent.setClass(holder.itemView.getContext(), SearchMainActivity.class);
        holder.itemView.setOnClickListener(v -> {
            holder.itemView.getContext().startActivity(intent);
        });
    }

    class SearchViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView date;
        private TextView author;
        private ImageView img;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.newsTitle);
            author = itemView.findViewById(R.id.author);
            date = itemView.findViewById(R.id.newsDate);
            img = itemView.findViewById(R.id.newsIMG);
        }
    }

}
