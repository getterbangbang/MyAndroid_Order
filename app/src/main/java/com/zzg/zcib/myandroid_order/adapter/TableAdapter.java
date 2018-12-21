package com.zzg.zcib.myandroid_order.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.zzg.zcib.myandroid_order.R;
import com.zzg.zcib.myandroid_order.utils.HttpServer;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;

import static com.zzg.zcib.myandroid_order.utils.HttpServer.IP_;

public class TableAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String,Object>> list;
    private ReflushListener  reflushListener;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_table_list,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.name=convertView.findViewById(R.id.item_table_num);
            viewHolder.statue=convertView.findViewById(R.id.item_table_statue);
            viewHolder.button=convertView.findViewById(R.id.item_table_btn);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(list.get(position).get("tablenum").toString()+"号桌");
        if ("0".equals(list.get(position).get("isempty").toString())){
            viewHolder.statue.setText("状态：空");
            viewHolder.button.setEnabled(false);
            viewHolder.button.setTextColor(Color.GRAY);

        }else {
            viewHolder.statue.setText("状态：满");
            viewHolder.button.setEnabled(true);
            viewHolder.button.setTextColor(Color.BLACK);
        }
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        HttpServer httpServer=new HttpServer();
                        String url=IP_+"/MyAndroid_Server_Order/userServlet";
                        String result= httpServer.postHtppByOkHttp(url,"clearTable",list.get(position).get("id").toString());
                        reflushListener.onFlush();
//                        Message message=handler.obtainMessage();
//                        message.obj=result;
//                        handler.sendMessage(message);
                    }

                }.start();
            }
        });


        return convertView;
    }

    private static class ViewHolder{
        TextView name;
        TextView statue;
        Button button;
    }


    public interface ReflushListener {
        public void onFlush();
    }
    public void setReflushListener (ReflushListener  reflushListener) {
        this.reflushListener = reflushListener;
    }


}
