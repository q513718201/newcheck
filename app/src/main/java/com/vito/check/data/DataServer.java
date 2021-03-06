package com.vito.check.data;

import com.vito.check.bean.WorkContent;
import com.vito.check.bean.WorkSelect;

import java.util.ArrayList;
import java.util.List;
public class DataServer {

    private DataServer() {
    }

    public static List<WorkSelect> getSampleData() {
        List<WorkSelect> list = new ArrayList<>();
        list.add(new WorkSelect(true, "工作管理", false));
        list.add(new WorkSelect(new WorkContent("我要派单")));
        list.add(new WorkSelect(new WorkContent("派单查询")));
        list.add(new WorkSelect(new WorkContent("日常巡检")));

        list.add(new WorkSelect(true, "统计分析", false));
        list.add(new WorkSelect(new WorkContent("报表统计")));
        list.add(new WorkSelect(new WorkContent("在线率查询")));
        list.add(new WorkSelect(true, "账户", false));
        list.add(new WorkSelect(new WorkContent("注销登录")));

        return list;
    }

}
