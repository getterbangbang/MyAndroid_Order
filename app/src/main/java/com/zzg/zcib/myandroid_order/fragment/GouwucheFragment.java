package com.zzg.zcib.myandroid_order.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.zzg.zcib.myandroid_order.R;
import com.zzg.zcib.myandroid_order.activity.MainActivity;
import com.zzg.zcib.myandroid_order.adapter.GouwucheAdapter;
import com.zzg.zcib.myandroid_order.utils.HttpServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static android.support.v4.os.LocaleListCompat.create;

public class GouwucheFragment extends Fragment {
    private ListView gouwucheList;
    public static TextView gouwuchePrize;
    private Button gouwucheBtn;
    private GouwucheAdapter gouwucheAdapter;
    private List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
    private String userid;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_gouwuche,container,false);
        gouwucheList=view.findViewById(R.id.gouwuchelist);
        gouwuchePrize=view.findViewById(R.id.cartprize);
        gouwucheBtn=view.findViewById(R.id.btn_gouwuche);

        gouwucheAdapter=new GouwucheAdapter(getContext(),list);
        gouwucheList.setAdapter(gouwucheAdapter);
        initData();
        gouwucheBtn.setOnClickListener(new BuyClick());
        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh_gouwuche);

        swipeRefreshLayout.setOnRefreshListener(new Refresh());


        return view;
    }

    private class Refresh implements SwipeRefreshLayout.OnRefreshListener{

        @Override
        public void onRefresh() {

            initData();

            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            initData();

//            Log.d("aaaaaaasdddddddddddd",data.toString());
        }
    }

    private class BuyClick implements View.OnClickListener{


        @Override
        public void onClick(View v) {
//            Log.d("hhhhhhhhhhhhhhhhh",MainActivity.tableid);
            String orderids="";
            if (list.size()!=0){
                for (int i = 0; i < list.size(); i++) {
                    orderids=orderids+list.get(i).get("id").toString()+",";
                }
                final JSONObject jsonObject =new JSONObject();
                try {
                    jsonObject.put("ordername",list.get(0).get("foodname").toString()+"等");
                    jsonObject.put("userid",userid);
                    jsonObject.put("orderids",orderids);
                    jsonObject.put("prizesum",gouwuchePrize.getText().toString());
                    jsonObject.put("tableid",MainActivity.tableid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //购买按钮，生成订单
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        String url="http://10.0.2.2:8080/MyAndroid_Server_Order/userServlet";
                        HttpServer httpServer=new HttpServer();

                        String result=httpServer.postHtppByOkHttp(url,"creatOrder",jsonObject.toString());
                        initData();

                    }
                }.start();

                Toast.makeText(getContext(),"订单已生成",Toast.LENGTH_SHORT).show();
            }

        }
    }


    private void initData(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                String url="http://10.0.2.2:8080/MyAndroid_Server_Order/userServlet";
                HttpServer httpServer=new HttpServer();
//                Log.d("dddddddddd",userid);
                String result=httpServer.postHtppByOkHttp(url,"getCart",userid);
                System.out.println("--------------------result:"+result);
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
                list.clear();
//                Gson gson=new Gson();

//                List<Map<String ,Object>> list2=gson.fromJson(msg.obj.toString(),new TypeToken<List<Map<String,Object>>>(){}.getType());
//                net.sf.json.JSONArray jsonArray=new net.sf.json.JSONArray(msg.obj.toString());
                List<Map<String ,Object>> list2= JSON.parseObject(msg.obj.toString(),new TypeReference<List<Map<String,Object>>>(){}); // Json 转List
                list.addAll(list2);
                int sumprice=0;
                for (int i = 0; i < list.size(); i++) {
                    int prize=Integer.parseInt(list.get(i).get("prize").toString());
                    int count=Integer.parseInt(list.get(i).get("count").toString());
                    sumprice=sumprice+prize*count;
                }
                gouwuchePrize.setText(sumprice+"");
                gouwucheAdapter.notifyDataSetChanged();
            }
        }
    };


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        userid = ((MainActivity) activity).getUserid();

    }
}
