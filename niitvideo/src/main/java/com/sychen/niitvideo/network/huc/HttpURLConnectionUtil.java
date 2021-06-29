package com.sychen.niitvideo.network.huc;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class HttpURLConnectionUtil {
    public static final String BASE_URL = "http://39.101.177.93:8098/";
    public static final String TAG = "HttpURLConnectionUtil";
    private OutputStream outputStream;
    private BufferedWriter bufferedWriter;

    //进行post请求
    public String doPost(String httpUrl, String param) {
        String result = "";
        try {
            URL url = new URL(BASE_URL + httpUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //设置请求的方法为POST
            urlConnection.setRequestMethod("POST");
            //允许写出
            urlConnection.setDoOutput(true);
            //允许读入
            urlConnection.setDoInput(true);
            //Content-Type
            urlConnection.setRequestProperty("accept", "application/json");
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("Cache-Control", "no-cache");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            //进行网络连接
            urlConnection.connect();
            //打开流，发送数据
            outputStream = urlConnection.getOutputStream();
            bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)
            );
            //添加参数
            String connect = "video-id=" + URLEncoder.encode(param, "UTF-8");
            bufferedWriter.write(connect);
            bufferedWriter.flush();
            int responseCode = urlConnection.getResponseCode();
            Log.e(TAG, "doPost: " + responseCode);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            result = bufferedReader.readLine();
            Log.e(TAG, "doPost: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
