package com.vito.check.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.vito.check.Activity.DeviceCheckActivity;
import com.vito.check.Activity.FinishOrderActivity;
import com.vito.check.Activity.SendOrderActivity;
import com.vito.check.R;
import com.vito.check.bean.MyOrder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by xk on 2017/3/20.
 */
public class MyAdapter extends BaseAdapter implements View.OnClickListener {
    private List<MyOrder.ContentBean> content;
    private Context context;
    private MyOrder.ContentBean mOrderBean;

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
            holder.tv_orderno= (TextView) convertView.findViewById(R.id.tv_orderno);
            holder.tv_desc= (TextView) convertView.findViewById(R.id.tv_desc);
            holder.tv_address= (TextView) convertView.findViewById(R.id.tv_address);
            holder.tv_state= (TextView) convertView.findViewById(R.id.tv_state);
            holder.bt_check= (Button) convertView.findViewById(R.id.bt_check);
            holder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
          //  holder.bt_order= (Button) convertView.findViewById(R.id.bt_send);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        holder.tv_no.setText(content.get(position).getDevNo());
        holder.tv_time.setText("派单时间:"+ getTime(content.get(position).getStartDate()));
        holder.tv_orderno.setText("派单编号："+content.get(position).getId()+"");
        holder.tv_desc.setText("故障描述:"+content.get(position).getDescription());
        holder.tv_address.setText("设备地址:"+content.get(position).getAddress());
        holder.tv_state.setText(content.get(position).getState());
        if(content.get(position).getState().equals("已结单")||content.get(position).getState().equals("待审核")){
            holder.bt_check.setVisibility(View.GONE);
            //holder.bt_order.setVisibility(View.GONE);
        }
        mOrderBean = content.get(position);
        holder.bt_check.setOnClickListener(this);
        //holder.bt_order.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_check:
                Intent check=new Intent(context, FinishOrderActivity.class);
                check.putExtra("orderBean",mOrderBean);
                check.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(check);

                break;
//            case R.id.bt_send:
//                Intent order=new Intent(context, SendOrderActivity.class);
//                order.putExtra("orderBean",mOrderBean);
//                order.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(order);
//                break;
        }
    }


    public class ViewHolder {
        public TextView tv_no;
        public TextView tv_orderno;
        public TextView tv_desc;
        public TextView tv_address;
        public TextView tv_state;
        public TextView tv_time;
        public Button   bt_check;
       // public Button   bt_order;
    }

    public  String getTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormater.format(date).toString();
    }

}
