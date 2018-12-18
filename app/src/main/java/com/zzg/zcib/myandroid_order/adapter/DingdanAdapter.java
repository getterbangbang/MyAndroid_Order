package com.zzg.zcib.myandroid_order.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zzg.zcib.myandroid_order.R;
import com.zzg.zcib.myandroid_order.activity.DetailsActivity;

import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import cn.jpush.android.api.JPushInterface;

public class DingdanAdapter extends BaseAdapter{

    private Context context;
    private List<Map<String,Object>> list;

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
        viewHolder.name.setText(list.get(position).get("ordername").toString()+"("+list.get(position).get("prizesum").toString()+")");
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
}
