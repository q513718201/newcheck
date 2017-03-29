package com.vito.check.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.vito.check.Activity.AllUserActivity;
import com.vito.check.Activity.DeviceCheckActivity;
import com.vito.check.Activity.SendOrderActivity;
import com.vito.check.R;
import com.vito.check.bean.AllUsers;
import com.vito.check.bean.MyOrder;

import java.util.List;

/**
 * Created by xk on 2017/3/20.
 */
public class AllUserAdapter extends BaseAdapter {
    private List<AllUsers.ContentBean> content;
    private Context context;

    public AllUserAdapter(List<AllUsers.ContentBean> content, Context context) {
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
            convertView = View.inflate(context,R.layout.item_alluser, null);
            holder = new ViewHolder();
            holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        holder.tv_name.setText(content.get(position).getNick_name());


        return convertView;
    }



    public class ViewHolder {
        public TextView tv_name;
    }
}
