package com.zzg.zcib.myandroid_order.fragment;

import android.app.Activity;
import android.app.UiAutomation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zzg.zcib.myandroid_order.R;
import com.zzg.zcib.myandroid_order.activity.MainActivity;
import com.zzg.zcib.myandroid_order.activity.MenuListActivity;
import com.zzg.zcib.myandroid_order.adapter.MenuListAdapter;
import com.zzg.zcib.myandroid_order.utils.HttpServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShouyeFragment extends Fragment {
    private TextView searchText,rexiao,more;
    private ListView shouyeList;
    private MenuListAdapter menuListAdapter;
    private List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
    private String userid;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_shouye,container,false);


        View headerView=LayoutInflater.from(getContext()).inflate(R.layout.fragment_shouye_header,null);

        searchText=headerView.findViewById(R.id.search_top);
        rexiao=headerView.findViewById(R.id.shouye_rexiao);
        more=headerView.findViewById(R.id.shouye_more);
        shouyeList=view.findViewById(R.id.shouyelist);

        shouyeList.addHeaderView(headerView);
        menuListAdapter=new MenuListAdapter(getContext(),list,userid);
        shouyeList.setAdapter(menuListAdapter);
        more.setOnClickListener(new MoreClick());

        initData();
        return view;
    }


    private class MoreClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(getContext(), MenuListActivity.class);
            intent.putExtra("userid",userid);
            getActivity().startActivity(intent);
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
                String result=httpServer.postHtppByOkHttp(url,"getHotList",userid);
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
                menuListAdapter.notifyDataSetChanged();
            }
        }
    };



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        userid = ((MainActivity) activity).getUserid();
    }

}
