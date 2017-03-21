package com.vito.check.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.vito.check.R;
import com.vito.check.bean.MyOrder;
import java.util.List;

/**
 * Created by xk on 2017/3/20.
 */
public class MyAdapter extends BaseAdapter {
    private List<MyOrder.ContentBean> content;
    private Context context;

    public MyAdapter(List<MyOrder.ContentBean> content,Context context) {
        this.content=content;
        this.context=context;
    }

    @Override
    public int getCount() {
        if(content.size()!=0){
            return content.size();
        }else{
            return 0;
        }

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


        ViewHolder holder ;
        if (convertView == null) {
            convertView = View.inflate(context,R.layout.item_myorder, null);
            holder = new ViewHolder();

            holder.tv_no= (TextView) convertView.findViewById(R.id.tv_no);
            holder.tv_type= (TextView) convertView.findViewById(R.id.tv_type);
            holder.tv_desc= (TextView) convertView.findViewById(R.id.tv_desc);
            holder.tv_address= (TextView) convertView.findViewById(R.id.tv_address);
            holder.tv_state= (TextView) convertView.findViewById(R.id.tv_state);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        holder.tv_no.setText(content.get(position).getDevNo());
        holder.tv_type.setText("故障类型:"+content.get(position).getType());
        holder.tv_desc.setText("故障描述:"+content.get(position).getDescription());
        holder.tv_address.setText("设备地址:"+content.get(position).getDevAddress());
        holder.tv_state.setText(content.get(position).getState());
        return convertView;
    }


    public class ViewHolder {
        public TextView tv_no;
        public TextView tv_type;
        public TextView tv_desc;
        public TextView tv_address;
        public TextView tv_state;
    }

}
