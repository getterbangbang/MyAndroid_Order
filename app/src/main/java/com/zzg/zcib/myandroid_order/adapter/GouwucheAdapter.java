package com.zzg.zcib.myandroid_order.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.zzg.zcib.myandroid_order.R;
import com.zzg.zcib.myandroid_order.fragment.GouwucheFragment;
import com.zzg.zcib.myandroid_order.utils.HttpServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class GouwucheAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,Object>> list;

    public GouwucheAdapter(Context context, List<Map<String, Object>> list) {
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
        final ViewHolder viewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_gouwuche,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.name=convertView.findViewById(R.id.item_gouwuche_name);
            viewHolder.prizeSum=convertView.findViewById(R.id.item_gouwuche_prizesum);
            viewHolder.count=convertView.findViewById(R.id.item_gouwuche_count);
            viewHolder.add=convertView.findViewById(R.id.item_gouwuche_addbtn);
            viewHolder.decrease=convertView.findViewById(R.id.item_gouwuche_decreasebtn);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(list.get(position).get("foodname").toString());
        viewHolder.count.setText(list.get(position).get("count").toString());
        final TextView countTextView=viewHolder.count;
        int count=Integer.parseInt(list.get(position).get("count").toString());
        int prize=Integer.parseInt(list.get(position).get("prize").toString());
        viewHolder.prizeSum.setText((count*prize)+"");
        viewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=Integer.parseInt(countTextView.getText().toString())+1;
                viewHolder.count.setText(count+"");
                viewHolder.prizeSum.setText(count*Integer.parseInt(list.get(position).get("prize").toString())+"");
                GouwucheFragment.gouwuchePrize.setText(Integer.parseInt(GouwucheFragment.gouwuchePrize.getText().toString())+Integer.parseInt(list.get(position).get("prize").toString())+"");
                setCount(list.get(position).get("id").toString(),count+"");
            }
        });
        viewHolder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=Integer.parseInt(countTextView.getText().toString())-1;
                viewHolder.count.setText(count+"");
                viewHolder.prizeSum.setText(count*Integer.parseInt(list.get(position).get("prize").toString())+"");
                GouwucheFragment.gouwuchePrize.setText(Integer.parseInt(GouwucheFragment.gouwuchePrize.getText().toString())-Integer.parseInt(list.get(position).get("prize").toString())+"");
                setCount(list.get(position).get("id").toString(),count+"");
            }
        });

        return convertView;
    }

    private void setCount(String id,String count){
        final JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("count",count);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new Thread(){
            @Override
            public void run() {
                super.run();
                HttpServer httpServer=new HttpServer();
                String url="http://10.0.2.2:8080/MyAndroid_Server_Order/userServlet";
                String result= httpServer.postHtppByOkHttp(url,"updateFoodCount",jsonObject.toString());

//                        Message message=handler.obtainMessage();
//                        message.obj=result;
//                        handler.sendMessage(message);
            }

        }.start();
    }

    private static class ViewHolder {
        TextView name;
        TextView prizeSum;
        Button add;
        Button decrease;
        TextView count;
    }
}
