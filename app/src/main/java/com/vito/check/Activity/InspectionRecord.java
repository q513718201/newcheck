package com.vito.check.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.vito.check.Adapter.ExpandableItemAdapter;
import com.vito.check.R;
import com.vito.check.bean.Level0Item;
import com.vito.check.bean.Person;

import java.util.ArrayList;
import java.util.Random;

public class InspectionRecord extends BaseActivity {
    private    RecyclerView               mRecyclerView;
    private ArrayList<MultiItemEntity> list;
    private ExpandableItemAdapter      adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("巡检记录",true);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = generateData();
        adapter = new ExpandableItemAdapter(list);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.setAdapter(adapter);
//      expandAll();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_inspection_record;
    }

    private void expandAll() {
        for (int i = 0; i <list.size() ; i++) {
            adapter.expand(i + adapter.getHeaderLayoutCount(), false, false);
        }
    }

    private ArrayList<MultiItemEntity> generateData() {
        int lv0Count = 2;
        int personCount = 5;

        String[] nameList = {"Bob", "Andy", "Lily", "Brown", "Bruce"};
        String[] level0 = {"日常巡检", "派单处理"};

        Random random = new Random();

        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < level0.length; i++) {
            Level0Item lv0 = new Level0Item(level0[i], "subtitle of " + i);
            for (int k = 0; k < personCount; k++) {
                lv0.addSubItem(new Person(nameList[k],nameList[k]+"--子类", random.nextInt(40)));
            }
            res.add(lv0);
        }
        return res;
    }
}
