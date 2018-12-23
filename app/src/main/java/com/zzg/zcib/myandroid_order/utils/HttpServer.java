package com.zzg.zcib.myandroid_order.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpServer {

    public static String IP_="http://39.108.220.36:8080";
//    public static String IP_="http://10.0.2.2:8080";

    public String getHtppByOkHttp(String url){
        String result=null;
        //创建okhttpClient对象
        try {
            OkHttpClient client=new OkHttpClient();
            //创建request
            Request request=new Request.Builder().url(url).build();
            //创建respose
            Response response=client.newCall(request).execute();
            if (response.isSuccessful()){
               result=response.message();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public String postHtppByOkHttp(String url,String action,String s){
        String result=null;
        //创建okhttpClient对象
        try {
            OkHttpClient client=new OkHttpClient();
            //创建request
//            MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
//            RequestBody body = RequestBody.create(JSON, jsonArray.toString());
            RequestBody requestBody = new FormBody.Builder()
                    .add("s", s)
                    .add("action",action)
                    .build();

            Request request=new Request.Builder().url(url).post(requestBody).build();
            //创建respose
            Response response=client.newCall(request).execute();

            if (response.isSuccessful()){
                result=response.body().string();
//                Log.d("ssssssssssss",result);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Bitmap getBitmapByOkHttp(String url){
        OkHttpClient client = new OkHttpClient();

        //获取请求对象
        Request request = new Request.Builder().url(url).build();

        //获取响应体

        ResponseBody body = null;
        try {
            body = client.newCall(request).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取流
        InputStream in = body.byteStream();
        //转化为bitmap
        Bitmap bitmap = BitmapFactory.decodeStream(in);

        return bitmap;
    }
}
