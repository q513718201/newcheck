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
        list.add(new WorkSelect(true, "巡检", false));
        list.add(new WorkSelect(new WorkContent("派单")));
        list.add(new WorkSelect(new WorkContent("在线率查询")));

        list.add(new WorkSelect(true, "管理", false));
        list.add(new WorkSelect(new WorkContent("报表")));
        list.add(new WorkSelect(new WorkContent("考核")));

        list.add(new WorkSelect(true, "账户", false));
        list.add(new WorkSelect(new WorkContent("注销登录")));

        return list;
    }

}
