package com.zzg.zcib.myandroid_order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zzg.zcib.myandroid_order.R;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;

public class TableAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String,Object>> list;

    public TableAdapter(Context context, List<Map<String, Object>> list) {
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
            convertView= LayoutInflater.from(context).inflate(R.layout.item_table_list,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.name=convertView.findViewById(R.id.item_table_num);
            viewHolder.statue=convertView.findViewById(R.id.item_table_statue);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(list.get(position).get("tablenum").toString()+"号桌");
        if ("0".equals(list.get(position).get("isempty").toString())){
            viewHolder.statue.setText("状态：空");
        }else {
            viewHolder.statue.setText("状态：满");
        }

        return convertView;
    }

    private static class ViewHolder{
        TextView name;
        TextView statue;
    }
}
