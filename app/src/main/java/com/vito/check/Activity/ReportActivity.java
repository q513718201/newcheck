package com.vito.check.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.vito.check.R;

/**
 * Created by xk on 2017/3/21.
 */

public class ReportActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("报表",true);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_report;
    }
}
