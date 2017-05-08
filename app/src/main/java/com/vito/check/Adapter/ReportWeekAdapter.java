package com.vito.check.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.vito.check.Activity.DayReportActivity;
import com.vito.check.Activity.FinishOrderActivity;
import com.vito.check.Activity.WeekReportActivity;
import com.vito.check.R;
import com.vito.check.bean.DayReport;
import com.vito.check.bean.MyOrder;
import com.vito.check.bean.WeekReport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by xk on 2017/3/20.
 */
public class ReportWeekAdapter extends BaseAdapter {
    private List<WeekReport.ContentBean> content;
    private Context context;
    private WeekReport.ContentBean mOrderBean;

    public ReportWeekAdapter(List<WeekReport.ContentBean> content, Context context) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {


        ViewHolder holder ;
        if (convertView == null) {
            convertView = View.inflate(context,R.layout.item_report, null);
            holder = new ViewHolder();
            holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_userful_num= (TextView) convertView.findViewById(R.id.tv_userful_num);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(content.get(position).getNickName());
        holder.tv_userful_num.setText("有效巡检量:"+content.get(position).getXjNumWeek() );
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.onClickWeek(content.get(position));
                }
            }
        });
        return convertView;
    }


    public class ViewHolder {
        public TextView tv_name;
        public TextView tv_userful_num;
    }

    private OnClickWeekListener listener;

    public void setOnClickWeek(OnClickWeekListener listener) {
        this.listener = listener;
    }

    public interface OnClickWeekListener {
        void onClickWeek(WeekReport.ContentBean mOrderBean);
    }

}
