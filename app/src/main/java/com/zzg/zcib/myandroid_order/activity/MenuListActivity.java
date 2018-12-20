package com.zzg.zcib.myandroid_order.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zzg.zcib.myandroid_order.R;
import com.zzg.zcib.myandroid_order.adapter.MenuListAdapter;
import com.zzg.zcib.myandroid_order.adapter.TypeListAdapter;
import com.zzg.zcib.myandroid_order.utils.HttpServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.zzg.zcib.myandroid_order.utils.HttpServer.IP_;

public class MenuListActivity extends AppCompatActivity {
    private ListView typeList,menuList;
    private String userid;
    private TypeListAdapter typeListAdapter;
    private MenuListAdapter menuListAdapter;
    private List<Map<String,Object>> typeData=new ArrayList<Map<String,Object>>();
    private List<Map<String,Object>> menuData=new ArrayList<Map<String,Object>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        userid=getIntent().getExtras().getString("userid");
        typeList=findViewById(R.id.typelist);
        menuList=findViewById(R.id.menulist);

        typeListAdapter=new TypeListAdapter(MenuListActivity.this,typeData);
        typeList.setAdapter(typeListAdapter);

        menuListAdapter=new MenuListAdapter(MenuListActivity.this,menuData,userid);
        menuList.setAdapter(menuListAdapter);
        initTypeData();
        initMenuData();

        typeList.setOnItemClickListener(new TypeClick());

    }

    private class TypeClick implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final String typeId=typeData.get(position).get("id").toString();
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    HttpServer httpServer=new HttpServer();
                    String url=IP_+"/MyAndroid_Server_Order/userServlet";
                    String result= httpServer.postHtppByOkHttp(url,"getOneMenu",typeId);

                    Message message=menuHandler.obtainMessage();
                    message.obj=result;
                    menuHandler.sendMessage(message);
                }

            }.start();
        }
    }




    private void initTypeData(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                HttpServer httpServer=new HttpServer();
                String url=IP_+"/MyAndroid_Server_Order/userServlet";
                String result= httpServer.postHtppByOkHttp(url,"getTypeData","");

                Message message=typeHandler.obtainMessage();
                message.obj=result;
                typeHandler.sendMessage(message);
            }

        }.start();
    }


    private void initMenuData(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                String url=IP_+"/MyAndroid_Server_Order/userServlet";
                HttpServer httpServer=new HttpServer();
//                Log.d("dddddddddd",userid);
                String result=httpServer.postHtppByOkHttp(url,"getHotList",userid);
//                System.out.println("--------------------result:"+result);
                Message message=menuHandler.obtainMessage();
                message.obj=result;
                menuHandler.sendMessage(message);
            }
        }.start();

    }

    private Handler typeHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj!=null){
                typeData.clear();
//                Gson gson=new Gson();
//                List<Map<String ,Object>> list2=gson.fromJson(msg.obj.toString(),new TypeToken<List<Map<String,Object>>>(){}.getType());
                List<Map<String ,Object>> list2= JSON.parseObject(msg.obj.toString(),new TypeReference<List<Map<String,Object>>>(){}); // Json 转List
                typeData.addAll(list2);
                typeListAdapter.notifyDataSetChanged();
            }

        }
    };

    private Handler menuHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj!=null){
                menuData.clear();
//                Gson gson=new Gson();
//                List<Map<String ,Object>> list2=gson.fromJson(msg.obj.toString(),new TypeToken<List<Map<String,Object>>>(){}.getType());
                List<Map<String ,Object>> list2= JSON.parseObject(msg.obj.toString(),new TypeReference<List<Map<String,Object>>>(){}); // Json 转List
                menuData.addAll(list2);
                menuListAdapter.notifyDataSetChanged();
            }

        }
    };

}
