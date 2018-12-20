package com.zzg.zcib.myandroid_order.adapter;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zzg.zcib.myandroid_order.R;
import com.zzg.zcib.myandroid_order.utils.HttpServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import static com.zzg.zcib.myandroid_order.utils.HttpServer.IP_;

public class MenuListAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,Object>> list;
    private String userid;

    public MenuListAdapter(Context context, List<Map<String, Object>> list,String userid) {
        this.context = context;
        this.list = list;
        this.userid=userid;
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
            convertView= LayoutInflater.from(context).inflate(R.layout.item_menulist,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.img=convertView.findViewById(R.id.img_food);
            viewHolder.name=convertView.findViewById(R.id.food_name);
            viewHolder.sales=convertView.findViewById(R.id.food_sales);
            viewHolder.prize=convertView.findViewById(R.id.food_prize);
            viewHolder.buyBtn=convertView.findViewById(R.id.btn_menulist);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.img.setBackgroundResource(R.drawable.cai);
        viewHolder.name.setText(list.get(position).get("foodname").toString());
        viewHolder.sales.setText("销量："+list.get(position).get("sales").toString());
        viewHolder.prize.setText("¥"+list.get(position).get("prize").toString());
        viewHolder.buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("userid",userid);
                    jsonObject.put("foodid",list.get(position).get("id").toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        HttpServer httpServer=new HttpServer();
                        String url=IP_+"/MyAndroid_Server_Order/userServlet";
                        String result= httpServer.postHtppByOkHttp(url,"addCart",jsonObject.toString());

//                        Message message=handler.obtainMessage();
//                        message.obj=result;
//                        handler.sendMessage(message);
                    }

                }.start();
                Toast.makeText(context,"已加入购物车",Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    private static class ViewHolder{
        private ImageView img;
        private TextView name;
        private TextView sales;
        private TextView prize;
        private Button buyBtn;
    }
}
