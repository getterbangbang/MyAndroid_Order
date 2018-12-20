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
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zzg.zcib.myandroid_order.R;
import com.zzg.zcib.myandroid_order.activity.MainActivity;
import com.zzg.zcib.myandroid_order.activity.WorkerMainActivity;
import com.zzg.zcib.myandroid_order.adapter.DingdanAdapter;
import com.zzg.zcib.myandroid_order.utils.HttpServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.zzg.zcib.myandroid_order.utils.HttpServer.IP_;

public class WorkerDingdanFragment extends Fragment {
    private List<Map<String ,Object>> list =new ArrayList<Map<String,Object>>();
    private DingdanAdapter dingdanAdapter;
    private ListView listView;
    private String userid;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_dingdan,container,false);
        listView=view.findViewById(R.id.dingdanlist);
        dingdanAdapter=new DingdanAdapter(getContext(),list);
        listView.setAdapter(dingdanAdapter);

        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh_dingdan);

        swipeRefreshLayout.setOnRefreshListener(new Refresh());

        initData();
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

    private void initData(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                String url=IP_+"/MyAndroid_Server_Order/userServlet";
                HttpServer httpServer=new HttpServer();
                Log.d("dddddddddd",userid);
                String result=httpServer.postHtppByOkHttp(url,"getWorkerAllDingdan",userid);
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
                List<Map<String ,Object>> list2= JSON.parseObject(msg.obj.toString(),new TypeReference<List<Map<String,Object>>>(){}); // Json 转List
                list.addAll(list2);
                dingdanAdapter.notifyDataSetChanged();
            }
        }
    };



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        userid = ((WorkerMainActivity) activity).getUserid();
    }
}
