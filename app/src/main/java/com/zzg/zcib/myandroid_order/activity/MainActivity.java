package com.zzg.zcib.myandroid_order.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zzg.zcib.myandroid_order.R;
import com.zzg.zcib.myandroid_order.fragment.ShouyeFragment;
import com.zzg.zcib.myandroid_order.fragment.WodeFragment;
import com.zzg.zcib.myandroid_order.fragment.GouwucheFragment;
import com.zzg.zcib.myandroid_order.fragment.DingdanFragment;
import com.zzg.zcib.myandroid_order.utils.HttpServer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class MainActivity extends AppCompatActivity {

    private RadioGroup menuRadioGroup;
    private RadioButton firstRadio;
    private ShouyeFragment shouyeFragment;
    private GouwucheFragment gouwucheFragment;
    private DingdanFragment dingdanFragment;
    private WodeFragment wodeFragment;
    private String userid;
    private TextView titleText;
    private Spinner spinner;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    public static String tableid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuRadioGroup=findViewById(R.id.radio_group_bottom);
        firstRadio=findViewById(R.id.diancan);
        titleText=findViewById(R.id.title_main);
        spinner=findViewById(R.id.table);

        //数据
        data_list = new ArrayList<String>();


        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);
        spinner.setOnItemSelectedListener(new TableSelected());




        userid=getIntent().getExtras().getString("userid");
//        Log.d("aaaaaaa",userid);
        menuRadioGroup.setOnCheckedChangeListener(new RadioChangeClick());
        firstRadio.setChecked(true);
        setTagAndAlias();

        initTableData();
    }

    private class TableSelected implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            tableid=data_list.get(position).split("号")[0];
            Log.d("tableid",tableid);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


    private void initTableData(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                HttpServer httpServer=new HttpServer();
                String url="http://10.0.2.2:8080/MyAndroid_Server_Order/userServlet";
                String result= httpServer.postHtppByOkHttp(url,"getTable",userid);

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
                for (int i = 0; i <list2.size() ; i++) {
                    data_list.add(list2.get(i).get("tablenum").toString()+"号");
                }
                arr_adapter.notifyDataSetChanged();
            }else {
                data_list.add("桌位已满");
            }

        }
    };


    public String getUserid(){
        return userid;
    }

    private class RadioChangeClick implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            allHide(fragmentTransaction);
            switch (checkedId){
                case R.id.diancan:
                    if (shouyeFragment ==null){
                        shouyeFragment =new ShouyeFragment();
                        fragmentTransaction.add(R.id.fragment_content, shouyeFragment);
                    }else {
                        fragmentTransaction.show(shouyeFragment);
                    }
                    titleText.setText("点餐");
                    break;
                case R.id.gouwuche:
                    if (gouwucheFragment ==null){
                        gouwucheFragment =new GouwucheFragment();
                        fragmentTransaction.add(R.id.fragment_content, gouwucheFragment);
                    }else {
                        fragmentTransaction.show(gouwucheFragment);
                    }
                    titleText.setText("购物车");
                    break;
                case R.id.dingdan:
                    if (dingdanFragment ==null){
                        dingdanFragment =new DingdanFragment();
                        fragmentTransaction.add(R.id.fragment_content, dingdanFragment);
                    }else {
                        fragmentTransaction.show(dingdanFragment);
                    }
                    titleText.setText("订单");
                    break;
                case R.id.wode:
                    if (wodeFragment ==null){
                        wodeFragment =new WodeFragment();
                        fragmentTransaction.add(R.id.fragment_content, wodeFragment);
                    }else {
                        fragmentTransaction.show(wodeFragment);
                    }
                    titleText.setText("个人信息");
                    break;
            }
            fragmentTransaction.commit();
        }
    }


    private void allHide(FragmentTransaction fragmentTransaction){
        if (shouyeFragment !=null){
            fragmentTransaction.hide(shouyeFragment);
        }
        if (gouwucheFragment !=null){
            fragmentTransaction.hide(gouwucheFragment);
        }
        if (dingdanFragment !=null){
            fragmentTransaction.hide(dingdanFragment);
        }
        if (wodeFragment !=null){
            fragmentTransaction.hide(wodeFragment);
        }
    }

    /**
     * 设置标签与别名
     */
    private void setTagAndAlias() {
        /**
         *这里设置了别名，在这里获取的用户登录的信息
         *并且此时已经获取了用户的userId,然后就可以用用户的userId来设置别名了
         **/
        //false状态为未设置标签与别名成功
        //if (UserUtils.getTagAlias(getHoldingActivity()) == false) {
        Set<String> tags = new HashSet<String>();
        //这里可以设置你要推送的人，一般是用户uid 不为空在设置进去 可同时添加多个
        if (!TextUtils.isEmpty(userid)){
            tags.add(userid);//设置tag
        }
        //上下文、别名【Sting行】、标签【Set型】、回调
        JPushInterface.setAliasAndTags(MainActivity.this, userid, tags,
                mAliasCallback);
        // }
    }

    /**
     * /**
     * TagAliasCallback类是JPush开发包jar中的类，用于
     * 设置别名和标签的回调接口，成功与否都会回调该方法
     * 同时给定回调的代码。如果code=0,说明别名设置成功。
     * /**
     * 6001   无效的设置，tag/alias 不应参数都为 null
     * 6002   设置超时    建议重试
     * 6003   alias 字符串不合法    有效的别名、标签组成：字母（区分大小写）、数字、下划线、汉字。
     * 6004   alias超长。最多 40个字节    中文 UTF-8 是 3 个字节
     * 6005   某一个 tag 字符串不合法  有效的别名、标签组成：字母（区分大小写）、数字、下划线、汉字。
     * 6006   某一个 tag 超长。一个 tag 最多 40个字节  中文 UTF-8 是 3 个字节
     * 6007   tags 数量超出限制。最多 100个 这是一台设备的限制。一个应用全局的标签数量无限制。
     * 6008   tag/alias 超出总长度限制。总长度最多 1K 字节
     * 6011   10s内设置tag或alias大于3次 短时间内操作过于频繁
     **/
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    //这里可以往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    //UserUtils.saveTagAlias(getHoldingActivity(), true);
                    logs = "Set tag and alias success极光推送别名设置成功";
                    Log.e("TAG", logs);
                    break;
                case 6002:
                    //极低的可能设置失败 我设置过几百回 出现3次失败 不放心的话可以失败后继续调用上面那个方面 重连3次即可 记得return 不要进入死循环了...
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.极光推送别名设置失败，60秒后重试";
                    Log.e("TAG", logs);
                    break;
                default:
                    logs = "极光推送设置失败，Failed with errorCode = " + code;
                    Log.e("TAG", logs);
                    break;
            }
        }
    };


}
