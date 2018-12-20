package com.zzg.zcib.myandroid_order.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zzg.zcib.myandroid_order.R;
import com.zzg.zcib.myandroid_order.utils.HttpServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.zzg.zcib.myandroid_order.utils.HttpServer.IP_;


public class RegisterActivity extends AppCompatActivity {


    private EditText regiUsername,regiPassword1,regiPassword2;
    private Button btnRegister,btnToLogin;
    private ImageView img;
    private String imgname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regiUsername=findViewById(R.id.regi_username);
        regiPassword1=findViewById(R.id.regi_password1);
        regiPassword2=findViewById(R.id.regi_password2);
//        regiPassword1.setTypeface(Typeface.DEFAULT);
//        regiPassword1.setTransformationMethod(new PasswordTransformationMethod());
//        regiPassword2.setTypeface(Typeface.DEFAULT);
//        regiPassword2.setTransformationMethod(new PasswordTransformationMethod());

        btnRegister=findViewById(R.id.btn_regi);
        btnToLogin=findViewById(R.id.btn_tologin);

        regiUsername.setOnFocusChangeListener(new HasUser());
        btnRegister.setOnClickListener(new RegisterClick());
        btnToLogin.setOnClickListener(new ToLoginClick());



    }









    private class RegisterClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if ("".equals(regiUsername.getText().toString())){
                Toast.makeText(RegisterActivity.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();

            }else if ("".equals(regiPassword1.getText().toString())){
                Toast.makeText(RegisterActivity.this,"密码不能为空！",Toast.LENGTH_SHORT).show();

            }else if ("".equals(regiPassword2.getText().toString())){
                Toast.makeText(RegisterActivity.this,"请再次输入密码！",Toast.LENGTH_SHORT).show();

            }else if (!regiPassword2.getText().toString().equals(regiPassword1.getText().toString())){
                Toast.makeText(RegisterActivity.this,"两次密码输入不相同！",Toast.LENGTH_SHORT).show();

            }else{
                final JSONObject jsonObject=new JSONObject();
                try {
                    System.out.println(regiUsername.getText().toString());
                    jsonObject.put("username",regiUsername.getText().toString());
                    jsonObject.put("password",regiPassword1.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //mainactivity 主线程不能做网路获取数据，启动一个子线程，网络数据加载
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        HttpServer httpServer=new HttpServer();
                        String url=IP_+"/MyAndroid_Server_Order/userServlet";
                        System.out.println(jsonObject.toString());
                        String result= httpServer.postHtppByOkHttp(url,"register",jsonObject.toString());

                        Message message=handler.obtainMessage();
                        message.obj=result;
                        handler.sendMessage(message);
                    }

                }.start();

            }

        }
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ((msg.obj).equals("no")){
                Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_LONG).show();
            }else{
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                startActivity(intent);
            }

        }
    };

    private Handler handler2=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ((msg.obj).equals("no")){
                Toast.makeText(RegisterActivity.this,"用户名不可用",Toast.LENGTH_LONG).show();
            }else{

                Toast.makeText(RegisterActivity.this,"用户名可用",Toast.LENGTH_LONG).show();

            }

        }
    };

    private class ToLoginClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            toLogin();
        }
    }

    private class HasUser implements View.OnFocusChangeListener{

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus){
                if("".equals(regiUsername.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();

                }else {
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            HttpServer httpServer=new HttpServer();
                            String url=IP_+"/MyAndroid_Server_Order/userServlet";
                            String result= httpServer.postHtppByOkHttp(url,"canRegister",regiUsername.getText().toString());

                            Message message=handler2.obtainMessage();
                            message.obj=result;
                            handler2.sendMessage(message);
                        }

                    }.start();
                }

            }
        }
    }

    private void toLogin(){
        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);

    }


}
