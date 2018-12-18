package com.zzg.zcib.myandroid_order.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zzg.zcib.myandroid_order.R;
import com.zzg.zcib.myandroid_order.utils.HttpServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginWorkerActivity extends AppCompatActivity {
    private EditText etUsername,etPassword;
    private Button button;
    private String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_worker);
        etUsername=findViewById(R.id.worker_login_username);
        etPassword=findViewById(R.id.worker_login_password);
        button=findViewById(R.id.worker_login_btn);

        button.setOnClickListener(new LoginClick());

    }

    private class LoginClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            final JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("username",etUsername.getText().toString());
                jsonObject.put("password",etPassword.getText().toString());
                jsonObject.put("role","worker");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //mainactivity 主线程不能做网路获取数据，启动一个子线程，网络数据加载
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    HttpServer httpServer=new HttpServer();
                    String url="http://10.0.2.2:8080/MyAndroid_Server_Order/userServlet";
                    String result= httpServer.postHtppByOkHttp(url,"login",jsonObject.toString());

                    Message message=handler.obtainMessage();
                    message.obj=result;
                    handler.sendMessage(message);
                }

            }.start();
        }
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ((msg.obj).equals("no")){
                Toast.makeText(LoginWorkerActivity.this,"用户名或密码错误",Toast.LENGTH_LONG).show();
            }else{
                Intent intent=new Intent(LoginWorkerActivity.this,WorkerMainActivity.class);
                Toast.makeText(LoginWorkerActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                userid=msg.obj.toString();
                intent.putExtra("userid",userid);
                startActivity(intent);
            }

        }
    };
}