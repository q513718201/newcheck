package com.vito.check.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.vito.check.Activity.FinishOrderActivity;
import com.vito.check.R;
import com.vito.check.bean.Gonggao;
import com.vito.check.bean.MyOrder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by xk on 2017/3/20.
 */
public class GonggaoAdapter extends BaseAdapter {
    private List<Gonggao.ContentBean> content;
    private Context context;
    private Gonggao.ContentBean mOrderBean;

    public GonggaoAdapter(List<Gonggao.ContentBean> content, Context context) {
        this.content = content;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (content.size() != 0) {
            return content.size();
        } else {
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


        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_gonggao, null);
            holder = new ViewHolder();

            holder.gonggao_name = (TextView) convertView.findViewById(R.id.gonggao_name);
            holder.gonggao_content = (TextView) convertView.findViewById(R.id.gonggao_content);
            holder.gonggao_date = (TextView) convertView.findViewById(R.id.gonggao_date);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        holder.gonggao_name.setText(content.get(position).getOwner()+":");
        holder.gonggao_content.setText(content.get(position).getContent());
        holder.gonggao_date.setText(content.get(position).getDateTime());

        mOrderBean = content.get(position);

        //holder.bt_order.setOnClickListener(this);
        return convertView;
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.bt_check:
//                Intent check = new Intent(context, FinishOrderActivity.class);
//                check.putExtra("orderBean", mOrderBean);
//                check.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(check);
//
//                break;
//
//        }
//    }


    public class ViewHolder {
        public TextView gonggao_name;
        public TextView gonggao_content;
        public TextView gonggao_date;


    }


}
