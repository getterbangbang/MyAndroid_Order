package com.zzg.zcib.myandroid_order.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zzg.zcib.myandroid_order.R;
import com.zzg.zcib.myandroid_order.activity.LoginActivity;
import com.zzg.zcib.myandroid_order.activity.MainActivity;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class WodeFragment extends Fragment {
    private Button btnLogout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_wode,container,false);

        btnLogout=view.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new LogoutClick());
        return view;
    }

    private class LogoutClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Set<String> tags = new HashSet<String>();
            //这里可以设置你要推送的人，一般是用户uid 不为空在设置进去 可同时添加多个

            tags.add("null");//设置tag

            //上下文、别名【Sting行】、标签【Set型】、回调
            JPushInterface.setAliasAndTags(getContext(), "null", tags,
                    mAliasCallback);

            Intent intent=new Intent(getContext(), LoginActivity.class);
            getActivity().startActivity(intent);
        }
    }



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
