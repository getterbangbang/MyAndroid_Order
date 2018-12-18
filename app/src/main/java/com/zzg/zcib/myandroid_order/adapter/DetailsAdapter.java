package com.zzg.zcib.myandroid_order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zzg.zcib.myandroid_order.R;

import java.util.List;
import java.util.Map;

public class DetailsAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,Object>> list;

    public DetailsAdapter(Context context, List<Map<String, Object>> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_details_menu,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.name=convertView.findViewById(R.id.item_details_name);
            viewHolder.price=convertView.findViewById(R.id.item_details_price);
            viewHolder.count=convertView.findViewById(R.id.item_details_count);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(list.get(position).get("foodname").toString());
        viewHolder.price.setText("¥"+list.get(position).get("prize").toString());
        viewHolder.count.setText("X"+list.get(position).get("count").toString());

        return convertView;
    }

    private static class ViewHolder{
        TextView name;
        TextView price;
        TextView count;
    }
}
