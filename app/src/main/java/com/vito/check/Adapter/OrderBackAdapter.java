package com.vito.check.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.vito.check.Activity.FinishOrderActivity;
import com.vito.check.Activity.SendYunyingOrderActivity;
import com.vito.check.R;
import com.vito.check.bean.YunyingOrder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by xk on 2017/3/20.
 */
public class OrderBackAdapter extends BaseAdapter  {
    private List<YunyingOrder.ContentBean> content;
    private Context context;
    private YunyingOrder.ContentBean mOrderBean;

    public OrderBackAdapter(List<YunyingOrder.ContentBean> content, Context context) {
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
            convertView = View.inflate(context, R.layout.item_backorder, null);
            holder = new ViewHolder();

            holder.tv_no = (TextView) convertView.findViewById(R.id.tv_no);
            holder.tv_orderno = (TextView) convertView.findViewById(R.id.tv_orderno);
            holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            holder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_branch = (TextView) convertView.findViewById(R.id.tv_branch);
            holder.bt_back = (Button) convertView.findViewById(R.id.back);

            holder.tv_paidanren = (TextView) convertView.findViewById(R.id.tv_paidanren);
            holder.tv_chuliren = (TextView) convertView.findViewById(R.id.tv_chuliren);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        holder.tv_no.setText("设备编号:"+content.get(position).getDevNo()+"");
        holder.tv_time.setText("派单时间:" + content.get(position).getStartDate()+"");
        holder.tv_orderno.setText("派单编号:" + content.get(position).getId() + "");
        holder.tv_desc.setText("故障描述:" + content.get(position).getDescription()+"");
        holder.tv_address.setText("设备地址:" + content.get(position).getDevAddress()+"");
        holder.tv_state.setText("派单状态:"+ content.get(position).getState()+"");
        holder.tv_branch.setText("派单部门:"+ content.get(position).getOwnerBranch()+"");
        holder.tv_paidanren.setText("派单人:"+ content.get(position).getOwnerNickName()+"");
        holder.tv_chuliren.setText("处理人:"+ content.get(position).getSendToNickName()+"");

        if (content.get(position).getState().equals("已结单") || content.get(position).getState().equals("待审核")) {
            holder.bt_back.setVisibility(View.GONE);
        }
        mOrderBean = content.get(position);
        holder.bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBackClick!=null){
                    mBackClick.back(mOrderBean);
                }
            }
        });

        return convertView;
    }

    public class ViewHolder {
        public TextView tv_no;
        public TextView tv_orderno;
        public TextView tv_desc;
        public TextView tv_address;
        public TextView tv_state;
        public TextView tv_time;
        public Button bt_back;
        public TextView tv_branch;
        public TextView tv_paidanren;
        public TextView tv_chuliren;
    }

    public String getTime(String time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormater.format(date).toString();
    }

    public OnBackClick mBackClick;
    public interface OnBackClick{
        void back(YunyingOrder.ContentBean mOrderBean);
    }
    public void setOnBack(OnBackClick mBackClick) {
        this.mBackClick = mBackClick;
    }

}
