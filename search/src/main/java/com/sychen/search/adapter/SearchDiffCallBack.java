package com.sychen.search.adapter;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.sychen.search.network.model.SearchNews;

public class SearchDiffCallBack extends DiffUtil.ItemCallback<SearchNews>{

    @Override
    public boolean areItemsTheSame(@NonNull SearchNews oldItem, @NonNull SearchNews newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @SuppressLint("DiffUtilEquals")
    @Override
    public boolean areContentsTheSame(@NonNull SearchNews oldItem, @NonNull SearchNews newItem) {
        return oldItem == newItem;
    }
}
