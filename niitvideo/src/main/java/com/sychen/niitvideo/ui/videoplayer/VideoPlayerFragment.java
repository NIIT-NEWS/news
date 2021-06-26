package com.sychen.niitvideo.ui.videoplayer;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sychen.niitvideo.R;

public class VideoPlayerFragment extends Fragment {

    private VideoPlayerViewModel mViewModel;
    private ViewPager2 viewPager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_pager, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPager = requireActivity().findViewById(R.id.videoViewPager);
        mViewModel = new ViewModelProvider(this).get(VideoPlayerViewModel.class);
        mViewModel.getVideos().observe(requireActivity(), video -> {
            viewPager.setAdapter(new FragmentStateAdapter(this) {
                @NonNull
                @Override
                public Fragment createFragment(int position) {
                    return  VideoFragment.newInstance(video.get(position));
                }

                @Override
                public int getItemCount() {
                    return video.size();
                }
            });
            viewPager.setOffscreenPageLimit(2);
        });
    }

}