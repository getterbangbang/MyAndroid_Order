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
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class ShouyeFragment extends Fragment implements ViewPager.OnPageChangeListener{
    private TextView searchText,rexiao,more;
    private ListView shouyeList;
    private MenuListAdapter menuListAdapter;
    private List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
    private String userid;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ViewPager viewPager;
    private int[] imageResIds;
    private ArrayList<ImageView> imageViewList;
    private LinearLayout ll_point_container;
    private String[] contentDescs;
    private TextView tv_desc;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;


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
        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh_shouye);

        swipeRefreshLayout.setOnRefreshListener(new Refresh());


        initData1();


        // 初始化布局 View视图
        viewPager = headerView.findViewById(R.id.viewpager);
        viewPager.setOnPageChangeListener(this);// 设置页面更新监听
//		viewPager.setOffscreenPageLimit(1);// 左右各保留几个对象
        ll_point_container = headerView.findViewById(R.id.ll_point_container);

        tv_desc = headerView.findViewById(R.id.tv_desc);

        // Model数据
        initData();

        // Controller 控制器
        initAdapter();

        // 开启轮询
        new Thread() {
            public void run() {
                isRunning = true;
                while (isRunning) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 往下跳一位
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            System.out.println("设置当前位置: " + viewPager.getCurrentItem());
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        }
                    });
                }
            }

            ;
        }.start();







        return view;
    }

    private class Refresh implements SwipeRefreshLayout.OnRefreshListener{

        @Override
        public void onRefresh() {

            initData1();

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


    private class MoreClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(getContext(), MenuListActivity.class);
            intent.putExtra("userid",userid);
            getActivity().startActivity(intent);
        }
    }



    private void initData1(){
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



    //轮播

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    private void initViews() {


    }

    /**
     * 初始化要显示的数据
     */
    private void initData() {
        // 图片资源id数组
        imageResIds = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e};

        // 文本描述
        contentDescs = new String[]{
                "家常豆腐",
                "金鸡独立",
                "一颗绿",
                "桃红",
                "红烧肉"
        };

        // 初始化要展示的5个ImageView
        imageViewList = new ArrayList<ImageView>();

        ImageView imageView;
        View pointView;
        LinearLayout.LayoutParams layoutParams;
        for (int i = 0; i < imageResIds.length; i++) {
            // 初始化要显示的图片对象
            imageView = new ImageView(getContext());
            imageView.setBackgroundResource(imageResIds[i]);
            imageViewList.add(imageView);

            // 加小白点, 指示器
            pointView = new View(getContext());
            pointView.setBackgroundResource(R.drawable.a);
            layoutParams = new LinearLayout.LayoutParams(5, 5);
            if (i != 0)
                layoutParams.leftMargin = 10;
            // 设置默认所有都不可用
            pointView.setEnabled(false);
            ll_point_container.addView(pointView, layoutParams);
        }
    }

    private void initAdapter() {
        ll_point_container.getChildAt(0).setEnabled(true);
        tv_desc.setText(contentDescs[0]);
        previousSelectedPosition = 0;

        // 设置适配器
        viewPager.setAdapter(new MyAdapter());

        // 默认设置到中间的某个位置
        int pos = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageViewList.size());
        // 2147483647 / 2 = 1073741823 - (1073741823 % 5)
        viewPager.setCurrentItem(5000000); // 设置到某个位置
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        // 3. 指定复用的判断逻辑, 固定写法
        @Override
        public boolean isViewFromObject(View view, Object object) {
//			System.out.println("isViewFromObject: "+(view == object));
            // 当划到新的条目, 又返回来, view是否可以被复用.
            // 返回判断规则
            return view == object;
        }

        // 1. 返回要显示的条目内容, 创建条目
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            System.out.println("instantiateItem初始化: " + position);
            // container: 容器: ViewPager
            // position: 当前要显示条目的位置 0 -> 4

//			newPosition = position % 5
            int newPosition = position % imageViewList.size();

            ImageView imageView = imageViewList.get(newPosition);
            // a. 把View对象添加到container中
            container.addView(imageView);
            // b. 把View对象返回给框架, 适配器
            return imageView; // 必须重写, 否则报异常
        }

        // 2. 销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // object 要销毁的对象
            System.out.println("destroyItem销毁: " + position);
            container.removeView((View) object);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        // 滚动时调用
    }

    @Override
    public void onPageSelected(int position) {
        // 新的条目被选中时调用
        System.out.println("onPageSelected: " + position);
        int newPosition = position % imageViewList.size();

        //设置文本
        tv_desc.setText(contentDescs[newPosition]);

//		for (int i = 0; i < ll_point_container.getChildCount(); i++) {
//			View childAt = ll_point_container.getChildAt(position);
//			childAt.setEnabled(position == i);
//		}
        // 把之前的禁用, 把最新的启用, 更新指示器
        ll_point_container.getChildAt(previousSelectedPosition).setEnabled(false);
        ll_point_container.getChildAt(newPosition).setEnabled(true);

        // 记录之前的位置
        previousSelectedPosition = newPosition;

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // 滚动状态变化时调用
    }


}
