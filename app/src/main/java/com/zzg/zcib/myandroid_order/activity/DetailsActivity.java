package com.zzg.zcib.myandroid_order.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zzg.zcib.myandroid_order.R;
import com.zzg.zcib.myandroid_order.adapter.DetailsAdapter;
import com.zzg.zcib.myandroid_order.utils.HttpServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

public class DetailsActivity extends AppCompatActivity {
    private TextView name,no,price,statue,time,table;
    private ListView menuList;
    private List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
    private DetailsAdapter detailsAdapter;
    private String orderid;
    private String orderids;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        name=findViewById(R.id.details_name);
        no=findViewById(R.id.details_no);
        price=findViewById(R.id.details_price);
        statue=findViewById(R.id.details_statue);
        time=findViewById(R.id.details_time);
        menuList=findViewById(R.id.details_menu_list);
        table=findViewById(R.id.details_table);

        detailsAdapter=new DetailsAdapter(DetailsActivity.this,list);
        menuList.setAdapter(detailsAdapter);



        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = getIntent().getExtras();
            String title = null;
            String content = null;
            if(bundle!=null){
                title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                content = bundle.getString(JPushInterface.EXTRA_ALERT);
                String[] content1=content.split("：");
                orderid=content1[1];
            }
        }
        initData();

    }

    private void initData(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                HttpServer httpServer=new HttpServer();
                String url="http://10.0.2.2:8080/MyAndroid_Server_Order/userServlet";
                String result= httpServer.postHtppByOkHttp(url,"getOneOrder",orderid);

                Message message=handler.obtainMessage();
                message.obj=result;
                handler.sendMessage(message);
            }

        }.start();
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj!=null){
                List<Map<String ,Object>> list2= JSON.parseObject(msg.obj.toString(),new TypeReference<List<Map<String,Object>>>(){}); // Json 转List
//                list.addAll(list2);
//                detailsAdapter.notifyDataSetChanged();
                name.setText(list2.get(0).get("ordername").toString());
                no.setText(list2.get(0).get("id").toString());
                price.setText(list2.get(0).get("prizesum").toString());
                statue.setText(list2.get(0).get("statue").toString());
                time.setText(list2.get(0).get("time").toString());
                table.setText(list2.get(0).get("tablenum").toString());
                orderids=list2.get(0).get("orderids").toString();
                initMenuListData();
            }

        }
    };

    private void initMenuListData(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                HttpServer httpServer=new HttpServer();
                String url="http://10.0.2.2:8080/MyAndroid_Server_Order/userServlet";
                String result= httpServer.postHtppByOkHttp(url,"getOneOrderMenu",orderids);

                Message message=handler2.obtainMessage();
                message.obj=result;
                System.out.println(orderids);
                handler2.sendMessage(message);
            }

        }.start();

    }

    private Handler handler2=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj!=null){
                List<Map<String ,Object>> list2= JSON.parseObject(msg.obj.toString(),new TypeReference<List<Map<String,Object>>>(){}); // Json 转List
                list.addAll(list2);
                detailsAdapter.notifyDataSetChanged();

            }

        }
    };


}
