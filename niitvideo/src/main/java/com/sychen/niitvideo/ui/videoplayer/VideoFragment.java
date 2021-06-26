package com.sychen.niitvideo.ui.videoplayer;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.sychen.niitvideo.R;
import com.sychen.niitvideo.network.model.Video;

import java.io.IOException;

public class VideoFragment extends Fragment {
    private static final String VIDEO_URL = "VIDEO_URL";
    private String getVideoUrl;
    private MediaPlayer myPlayer;
    private SurfaceHolder holder;

    public VideoFragment() {
    }

    public static VideoFragment newInstance(Video video) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putString(VIDEO_URL, video.getUrl());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getVideoUrl = getArguments().getString(VIDEO_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.video_player_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPlayer();
    }

    private void initPlayer() {
        SurfaceView surfaceView = requireActivity().findViewById(R.id.surface_view);
        myPlayer = new MediaPlayer();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                myPlayer.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

            }
        });
    }


    private void play() {
        myPlayer.reset();
        try {
            myPlayer.setDataSource(getVideoUrl);
            myPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        myPlayer.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        play();
    }



    @Override
    public void onPause() {
        super.onPause();
        myPlayer.pause();
    }

}