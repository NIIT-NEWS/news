package com.sychen.collect.adapter;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.sychen.collect.network.model.CollectNews;

public class CollectDiffCallBack extends DiffUtil.ItemCallback<CollectNews> {
    @Override
    public boolean areItemsTheSame(@NonNull CollectNews oldItem, @NonNull CollectNews newItem) {
        return oldItem.getNews().getId() == newItem.getNews().getId();
    }

    @SuppressLint("DiffUtilEquals")
    @Override
    public boolean areContentsTheSame(@NonNull CollectNews oldItem, @NonNull CollectNews newItem) {
        return oldItem == newItem;
    }
}
