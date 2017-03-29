package com.vito.check.EventBus;

import com.vito.check.bean.User;

/**
 * Created by xk on 2017/3/16.
 */

public class SendMessage {


        private String msg;
        public SendMessage(String msg) {
            // TODO Auto-generated constructor stub
            this.msg = msg;
        }
        public String getUserMessage(){
            return msg;
        }

}
