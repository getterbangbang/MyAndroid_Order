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

import static com.zzg.zcib.myandroid_order.utils.HttpServer.IP_;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername,etPassword;
    private Button button,goWorkerLogin,btnToRegister;
    private String userid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername=findViewById(R.id.login_username);
        etPassword=findViewById(R.id.login_password);
        button=findViewById(R.id.login_btn);
        goWorkerLogin=findViewById(R.id.btn_go_worker);
        btnToRegister=findViewById(R.id.btn_toregister);
        btnToRegister.setOnClickListener(new ToRegisterClick());

        button.setOnClickListener(new LoginClick());
        goWorkerLogin.setOnClickListener(new WorkerClick());

    }

    private class ToRegisterClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        }
    }

    private class WorkerClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(LoginActivity.this,LoginWorkerActivity.class);
            startActivity(intent);
        }
    }

    private class LoginClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            final JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("username",etUsername.getText().toString());
                jsonObject.put("password",etPassword.getText().toString());
                jsonObject.put("role","customer");
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
                Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_LONG).show();
            }else{
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                userid=msg.obj.toString();
                intent.putExtra("userid",userid);
                startActivity(intent);
            }

        }
    };
}
