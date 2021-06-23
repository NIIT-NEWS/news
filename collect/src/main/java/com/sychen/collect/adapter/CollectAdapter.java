package com.sychen.collect.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sychen.collect.R;
import com.sychen.collect.network.model.CollectNews;

public class CollectAdapter extends ListAdapter<CollectNews, CollectAdapter.CollectViewHolder> {
    public CollectAdapter(@NonNull DiffUtil.ItemCallback<CollectNews> diffCallback) {
        super(diffCallback);
    }
    @NonNull
    @Override
    public CollectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CollectViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.collect_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CollectViewHolder holder, int position) {
        CollectNews news = getItem(position);
        holder.title.setText(news.getNews().getNewTitle());
        holder.author.setText(news.getNews().getAuthor());
        holder.date.setText(news.getNews().getDate());
        Glide.with(holder.itemView.getContext())
                .load(news.getNews().getNewTitleImgUrl())
                .into(holder.img);
//        Intent intent = new Intent();
//        intent.putExtra("Title",news.getNews().getNewTitle());
//        intent.putExtra("Content",news.getNews().getContent());
//        intent.putExtra("Picture",news.getNews().getNewTitleImgUrl());
//        intent.setClass(holder.itemView.getContext(), CollectMainActivity.class);
//        holder.itemView.setOnClickListener(v -> {
//            holder.itemView.getContext().startActivity(intent);
//        });
    }

    class CollectViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView date;
        private TextView author;
        private ImageView img;
        public CollectViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.newsTitles);
            author = itemView.findViewById(R.id.authors);
            date = itemView.findViewById(R.id.newsDates);
            img = itemView.findViewById(R.id.newsIMGS);
        }
    }
}
