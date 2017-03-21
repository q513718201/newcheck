package com.vito.check.EventBus;

import com.vito.check.bean.User;

/**
 * Created by xk on 2017/3/16.
 */

public class SendMessage {


        private User user;
        public SendMessage(User user) {
            // TODO Auto-generated constructor stub
            this.user = user;
        }
        public User getUserMessage(){
            return user;
        }

}
