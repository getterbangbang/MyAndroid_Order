package com.zzg.zcib.myandroid_order.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zzg.zcib.myandroid_order.R;
import com.zzg.zcib.myandroid_order.activity.DetailsActivity;
import com.zzg.zcib.myandroid_order.utils.HttpServer;
import com.zzg.zcib.myandroid_order.utils.MyVolley;

import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import cn.jpush.android.api.JPushInterface;

import static com.zzg.zcib.myandroid_order.utils.HttpServer.IP_;

public class DingdanAdapter extends BaseAdapter{

    private Context context;
    private List<Map<String,Object>> list;
//    private ImageView imag;

    public DingdanAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_dingdanlist,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.imageView=convertView.findViewById(R.id.img_item_dingdan);
            viewHolder.name=convertView.findViewById(R.id.name_item_dingdan);
            viewHolder.time=convertView.findViewById(R.id.time_item_dingdan);
            viewHolder.statue=convertView.findViewById(R.id.statue_item_dingdan);
            viewHolder.details=convertView.findViewById(R.id.details_item_dingdan);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setBackgroundResource(R.drawable.cai);

//         imag=viewHolder.imageView;
//
//
//            String url=IP_+"/MyAndroid_Server_Order/imgs/a.jpg";
//            MyVolley.imageVolley(url, context, new MyVolley.ImgCallBack() {
//                @Override
//                public void onSuccess(Bitmap bitmap) {
//                    imag.setImageBitmap(bitmap);
//                }
//            });
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                HttpServer httpServer=new HttpServer();
//                String url=IP_+"/MyAndroid_Server_Order/imgs/a.jpg";
//                Bitmap result= httpServer.getBitmapByOkHttp(url);
//
//                Message message=handler.obtainMessage();
//                message.obj=result;
//                handler.sendMessage(message);
//            }
//
//        }.start();



        viewHolder.name.setText(list.get(position).get("ordername").toString()+"(¥"+list.get(position).get("prizesum").toString()+")");
        viewHolder.time.setText(list.get(position).get("time").toString());
        viewHolder.statue.setText(list.get(position).get("statue").toString());
        viewHolder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailsActivity.class);
                intent.putExtra(JPushInterface.EXTRA_NOTIFICATION_TITLE,"1");
                intent.putExtra(JPushInterface.EXTRA_ALERT,"id："+list.get(position).get("id").toString());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView name;
        TextView time;
        TextView statue;
        TextView details;
    }

//    Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//            imag.setImageBitmap((Bitmap) msg.obj);
//        }
//    };
}
