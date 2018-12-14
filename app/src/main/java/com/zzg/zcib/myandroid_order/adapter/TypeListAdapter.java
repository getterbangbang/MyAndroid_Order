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

public class TypeListAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String,Object>> list;

    public TypeListAdapter(Context context, List<Map<String, Object>> list) {
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
            convertView= LayoutInflater.from(context).inflate(R.layout.item_typelist,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.typename=convertView.findViewById(R.id.typename);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.typename.setText(list.get(position).get("typename").toString());

        return convertView;
    }

    private static class ViewHolder{
        TextView typename;
    }
}
