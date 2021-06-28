package com.sychen.niitvideo.ui.videoplayer;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sychen.niitvideo.R;
import com.sychen.niitvideo.network.model.Video;

import java.io.IOException;

public class VideoFragment extends Fragment {
    private static final String VIDEO_URL = "VIDEO_URL";
    private static final String VIDEO_Title = "VIDEO_Title";
    private static final String VIDEO_Visit_Count = "VIDEO_Visit_Count";
    public static final String TAG = "TAG";
    private String getVideoUrl;
    private String getVideoTitle;
    private String getVideoVisitCount;
    private SurfaceView surfaceView;
    private MediaPlayer myPlayer;
    private TextView videoTitle;
    private TextView videoVisitCount;
    private ProgressBar videoProgressBar;

    public VideoFragment() {
    }

    public static VideoFragment newInstance(Video video) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putString(VIDEO_URL, video.getUrl());
        args.putString(VIDEO_Title, video.getTitle());
        args.putString(VIDEO_Visit_Count, video.getVisitCount());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getVideoUrl = getArguments().getString(VIDEO_URL);
            getVideoTitle = getArguments().getString(VIDEO_Title);
            getVideoVisitCount = getArguments().getString(VIDEO_Visit_Count);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_player_fragment, container, false);
        surfaceView = view.findViewById(R.id.surface_view);
        videoTitle = view.findViewById(R.id.videoTitle);
        videoVisitCount = view.findViewById(R.id.videoVisitCount);
        videoProgressBar = view.findViewById(R.id.videoProgressBar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPlayer();
    }

    private void initPlayer() {
        videoTitle.setText(getVideoTitle);
        videoVisitCount.setText(getVideoVisitCount);
        myPlayer = new MediaPlayer();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
                if (holder!=null){
                    myPlayer.setDisplay(holder);
                }
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                if (myPlayer != null && myPlayer.isPlaying()) {
                    myPlayer.stop();
                    myPlayer.release();
                    myPlayer = null;
                }
            }
        });
    }

    private void initProgressBar() {
        int duration = myPlayer.getDuration();
        videoProgressBar.setProgress(0);
        videoProgressBar.setMax(duration);
        updateProgressBar();
    }

    private void updateProgressBar() {
        try {
            Message message = Message.obtain();
            message.arg1 = myPlayer.getCurrentPosition();
            handler.sendMessageDelayed(message, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int currentPosition = 0;
            try {
                currentPosition = myPlayer.getCurrentPosition();
            } catch (Exception e) {
                e.printStackTrace();
            }
            videoProgressBar.setProgress(currentPosition);
            updateProgressBar();
        }
    };

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

    private void pause() {
        if (myPlayer != null) {
            myPlayer.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        play();
        initProgressBar();
    }


    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (myPlayer != null && myPlayer.isPlaying()) {
            myPlayer.stop();
            myPlayer.release();
            myPlayer = null;
        }
    }
}