package com.vito.check.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.vito.check.R;
import com.vito.check.bean.Level0Item;
import com.vito.check.bean.Person;

import java.util.List;

/**
 * Created by luoxw on 2016/8/9.
 */
public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final String TAG = ExpandableItemAdapter.class.getSimpleName();

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_PERSON = 1;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ExpandableItemAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_expandable_lv0);
        addItemType(TYPE_PERSON, R.layout.item_expandable_lv1);

    }

    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
           case TYPE_LEVEL_0:
                final Level0Item lv0 = (Level0Item)item;
                holder.setText(R.id.title, lv0.title)
                        .setImageResource(R.id.iv, lv0.isExpanded() ? R.drawable.arrow_b : R.drawable.arrow_r);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        Log.d(TAG, "Level 1 item pos: " + pos);
                        if (lv0.isExpanded()) {
                            collapse(pos, true);
                        } else {
                            expand(pos, true);
                        }
                    }
                });
                break;
            case TYPE_PERSON:

                final Person person = (Person)item;
                holder.setText(R.id.tv, person.name + " parent pos: " + getParentPosition(person))
                .setText(R.id.tv_sub,person.subName);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = holder.getAdapterPosition();
                        Toast.makeText(mContext,pos+"",Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
