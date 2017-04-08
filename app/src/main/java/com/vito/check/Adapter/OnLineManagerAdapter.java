package com.vito.check.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vito.check.Activity.DayReportActivity;
import com.vito.check.Activity.OnlineActivity;
import com.vito.check.R;
import com.vito.check.bean.DayReport;
import com.vito.check.bean.OnlineRate;

import java.util.List;

/**
 * Created by xk on 2017/3/20.
 */
public class OnLineManagerAdapter extends BaseAdapter {
    private List<OnlineRate.ContentBean> content;
    private Context context;
    private OnlineRate.ContentBean mOrderBean;

    public OnLineManagerAdapter(List<OnlineRate.ContentBean> content, Context context) {
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
            convertView = View.inflate(context,R.layout.item_online_manager, null);
            holder = new ViewHolder();

            holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_nowrate= (TextView) convertView.findViewById(R.id.tv_nowrate);
            holder.tv_toprate= (TextView) convertView.findViewById(R.id.tv_toprate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        holder.tv_name.setText(content.get(position).getOwner());
        holder.tv_nowrate.setText(content.get(position).getNowOnlineRate() );
        holder.tv_toprate.setText(content.get(position).getTopOnlineRate() );
        mOrderBean = content.get(position);
        return convertView;
    }

    public class ViewHolder {
        public TextView tv_name;
        public TextView tv_nowrate;
        public TextView tv_toprate;
    }



}
