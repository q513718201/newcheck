package com.vito.check.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.vito.check.Activity.FinishOrderActivity;
import com.vito.check.Activity.SendOrderActivity;
import com.vito.check.Activity.SendYunyingOrderActivity;
import com.vito.check.R;
import com.vito.check.bean.MyOrder;
import com.vito.check.bean.YunyingOrder;
import com.vito.check.util.SpUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by xk on 2017/3/20.
 */
public class YunyingAdapter extends BaseAdapter implements View.OnClickListener {
    private  String extra;
    private List<YunyingOrder.ContentBean> content;
    private Context context;

    private YunyingOrder.ContentBean mOrderBean;

    public YunyingAdapter(List<YunyingOrder.ContentBean> content, Context context, String extra) {
        this.content = content;
        this.context = context;
        this.extra=extra;
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
            convertView = View.inflate(context, R.layout.item_yunyingorder, null);
            holder = new ViewHolder();

            holder.tv_no = (TextView) convertView.findViewById(R.id.tv_no);
            holder.tv_orderno = (TextView) convertView.findViewById(R.id.tv_orderno);
            holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            holder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            holder.bt_check = (Button) convertView.findViewById(R.id.bt_check);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_paidanren = (TextView) convertView.findViewById(R.id.tv_paidanren);
            holder.tv_chuliren = (TextView) convertView.findViewById(R.id.tv_chuliren);
            holder.tv_branch = (TextView) convertView.findViewById(R.id.tv_branch);
            holder.bt_send = (Button) convertView.findViewById(R.id.bt_send);
            holder.tv_zhuanpairen = (TextView) convertView.findViewById(R.id.tv_zhuanpairen);
            holder.tv_chulishijian = (TextView) convertView.findViewById(R.id.tv_chulishijian);
            holder.tv_jiedanshijian = (TextView) convertView.findViewById(R.id.tv_jiedanshijian);

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
        holder.tv_chulishijian.setText("处理时间:"+ content.get(position).getProcessDate()+"");
        holder.tv_jiedanshijian.setText("结单时间:"+ content.get(position).getFinishDate()+"");
        if(TextUtils.isEmpty(content.get(position).getTransforNickName())){
            holder.tv_zhuanpairen.setVisibility(View.GONE);
        }else{
            holder.tv_zhuanpairen.setText("转派人:"+ content.get(position).getTransforNickName()+"");
        }


        if(extra.equals("1")){
            holder.bt_check.setVisibility(View.GONE);
            holder.bt_send.setVisibility(View.GONE);
        }
        if (content.get(position).getState().equals("已结单") || content.get(position).getState().equals("待审核")) {
            holder.bt_check.setVisibility(View.GONE);
            holder.bt_send.setVisibility(View.GONE);
        }
        if(content.get(position).getState().equals("派单中")){
            holder.tv_chulishijian.setVisibility(View.GONE);
            holder.tv_jiedanshijian.setVisibility(View.GONE);
        }
        if(content.get(position).getState().equals("待审核")){
            holder.tv_jiedanshijian.setVisibility(View.GONE);
        }

        mOrderBean = content.get(position);
        holder.bt_check.setOnClickListener(this);
        holder.bt_send.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_check:
                Intent check = new Intent(context, FinishOrderActivity.class);
                check.putExtra("yunyingBean", mOrderBean);
                check.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(check);

                break;
            case R.id.bt_send:
                Intent order=new Intent(context, SendYunyingOrderActivity.class);
                order.putExtra("orderBean",mOrderBean);
                order.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(order);
                break;
        }
    }


    public class ViewHolder {
        public TextView tv_no;
        public TextView tv_orderno;
        public TextView tv_desc;
        public TextView tv_address;
        public TextView tv_state;
        public TextView tv_time;
        public Button bt_check;
        public TextView tv_paidanren;
        public TextView tv_chuliren;
        public TextView tv_branch;
        public TextView tv_zhuanpairen;
        public Button bt_send;
        public TextView tv_chulishijian;
        public TextView tv_jiedanshijian;
    }

    public String getTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormater.format(date).toString();
    }

}
