package com.sychen.niitvideo.ui.videoplayer;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sychen.basic.network.BaseResult;
import com.sychen.basic.util.SharedPreferencesUtil;
import com.sychen.niitvideo.R;
import com.sychen.niitvideo.network.huc.HttpURLConnectionUtil;
import com.sychen.niitvideo.network.model.LikeVideoModel;
import com.sychen.niitvideo.network.model.Video;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class VideoFragment extends Fragment {
    public static final String TAG = "TAG";
    private static Video videoInfo;
    private SurfaceView surfaceView;
    private MediaPlayer myPlayer;
    private TextView videoTitle;
    private TextView videoVisitCount;
    private TextView videodate;
    private TextView videoauthor;
    private ProgressBar videoProgressBar;
    private ProgressBar progressBar;
    private ImageView likevideo;
    private HttpURLConnectionUtil httpURLConn;

    public static VideoFragment newInstance(Video video) {
        VideoFragment fragment = new VideoFragment();
        videoInfo = video;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_player_fragment, container, false);
        surfaceView = view.findViewById(R.id.surface_view);
        videoTitle = view.findViewById(R.id.videoTitle);
        videoVisitCount = view.findViewById(R.id.videoVisitCount);
        videoProgressBar = view.findViewById(R.id.videoProgressBar);
        likevideo = view.findViewById(R.id.likevideo);
        videodate = view.findViewById(R.id.videodate);
        videoauthor = view.findViewById(R.id.videoauthor);
        progressBar = view.findViewById(R.id.progressBar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    @SuppressLint("SetTextI18n")
    private void initViews() {
        initPlayer();
        videoTitle.setText(videoInfo.getTitle());
        videoVisitCount.setText(videoInfo.getVisitCount());
        videodate.setText(videoInfo.getDate());
        videoauthor.setText(videoInfo.getAuthor());
        httpURLConn = new HttpURLConnectionUtil();
        likevideo.setOnClickListener(v ->
                new Thread(() -> {
                    String result = httpURLConn.doPost("video/likeVideos", String.valueOf(videoInfo.getId()));
                    LikeVideoModel likeVideoModel = new Gson().fromJson(result, LikeVideoModel.class);
                    if (likeVideoModel.getCode() == 200) {
                        Looper.prepare();
                        likevideo.setImageResource(R.drawable.ic_loved);
                        likevideo.setClickable(false);
                        requireActivity().runOnUiThread(new Runnable() {
                            @SuppressLint("ResourceType")
                            @Override
                            public void run() {
                                int visitCount = Integer.parseInt(videoInfo.getVisitCount());
                                Log.e(TAG, "run: "+visitCount );
//                                videoVisitCount.setText(visitCount+1);
                            }
                        });
                        Looper.loop();
                    }
                }).start()
        );
    }

    private void initPlayer() {
        myPlayer = new MediaPlayer();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
                if (holder != null) {
                    myPlayer.setDisplay(holder);
                }
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
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
            myPlayer.setDataSource(videoInfo.getUrl());
            myPlayer.setOnPreparedListener(preparedListener);
            myPlayer.prepareAsync();
            progressBar.setVisibility(View.VISIBLE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    MediaPlayer.OnPreparedListener preparedListener = mp -> {
        progressBar.setVisibility(View.INVISIBLE);
        initProgressBar();
        mp.start();
    };

    private void pause() {
        if (myPlayer != null) {
            myPlayer.pause();
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        play();
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