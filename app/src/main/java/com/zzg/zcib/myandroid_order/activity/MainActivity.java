package com.zzg.zcib.myandroid_order.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zzg.zcib.myandroid_order.R;
import com.zzg.zcib.myandroid_order.fragment.ShouyeFragment;
import com.zzg.zcib.myandroid_order.fragment.WodeFragment;
import com.zzg.zcib.myandroid_order.fragment.GouwucheFragment;
import com.zzg.zcib.myandroid_order.fragment.DingdanFragment;

public class MainActivity extends AppCompatActivity {

    private RadioGroup menuRadioGroup;
    private RadioButton firstRadio;
    private ShouyeFragment shouyeFragment;
    private GouwucheFragment gouwucheFragment;
    private DingdanFragment dingdanFragment;
    private WodeFragment wodeFragment;
    private String userid;
    private TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuRadioGroup=findViewById(R.id.radio_group_bottom);
        firstRadio=findViewById(R.id.diancan);
        titleText=findViewById(R.id.title_main);
        userid=getIntent().getExtras().getString("userid");
        Log.d("aaaaaaa",userid);
        menuRadioGroup.setOnCheckedChangeListener(new RadioChangeClick());
        firstRadio.setChecked(true);
    }


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
                    titleText.setText("我的订单");
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
}
