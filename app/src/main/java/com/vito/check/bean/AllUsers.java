package com.vito.check.bean;

import java.util.List;

/**
 * Created by xk on 2017/3/24.
 */

public class AllUsers {

    /**
     * content : [{"id":null,"nick_name":"陈浴","login_name":"wh004","password":null,"salt":null,"update_time":null,"create_time":null,"edit_user":null,"role_id":null,"role_name":null},{"id":null,"nick_name":"蔺伟东","login_name":"xj003","password":null,"salt":null,"update_time":null,"create_time":null,"edit_user":null,"role_id":null,"role_name":null},{"id":null,"nick_name":"王彦锋","login_name":"xj005","password":null,"salt":null,"update_time":null,"create_time":null,"edit_user":null,"role_id":null,"role_name":null},{"id":null,"nick_name":"梁耀安","login_name":"xj008","password":null,"salt":null,"update_time":null,"create_time":null,"edit_user":null,"role_id":null,"role_name":null},{"id":null,"nick_name":"陈智俊","login_name":"wh001","password":null,"salt":null,"update_time":null,"create_time":null,"edit_user":null,"role_id":null,"role_name":null},{"id":null,"nick_name":"赵生录","login_name":"xj013","password":null,"salt":null,"update_time":null,"create_time":null,"edit_user":null,"role_id":null,"role_name":null},{"id":null,"nick_name":"刘洋","login_name":"wh002","password":null,"salt":null,"update_time":null,"create_time":null,"edit_user":null,"role_id":null,"role_name":null},{"id":null,"nick_name":"史豪杰","login_name":"xj018","password":null,"salt":null,"update_time":null,"create_time":null,"edit_user":null,"role_id":null,"role_name":null},{"id":null,"nick_name":"赵国锋","login_name":"xj014","password":null,"salt":null,"update_time":null,"create_time":null,"edit_user":null,"role_id":null,"role_name":null},{"id":null,"nick_name":"柴长进","login_name":"xj001","password":null,"salt":null,"update_time":null,"create_time":null,"edit_user":null,"role_id":null,"role_name":null},{"id":null,"nick_name":"李建强","login_name":"xj015","password":null,"salt":null,"update_time":null,"create_time":null,"edit_user":null,"role_id":null,"role_name":null},{"id":null,"nick_name":"孙建刚","login_name":"xj016","password":null,"salt":null,"update_time":null,"create_time":null,"edit_user":null,"role_id":null,"role_name":null},{"id":null,"nick_name":"林耀强","login_name":"xj017","password":null,"salt":null,"update_time":null,"create_time":null,"edit_user":null,"role_id":null,"role_name":null},{"id":null,"nick_name":"陈沐","login_name":"wh003","password":null,"salt":null,"update_time":null,"create_time":null,"edit_user":null,"role_id":null,"role_name":null}]
     * success : true
     */

    private boolean success;
    private List<ContentBean> content;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * id : null
         * nick_name : 陈浴
         * login_name : wh004
         * password : null
         * salt : null
         * update_time : null
         * create_time : null
         * edit_user : null
         * role_id : null
         * role_name : null
         */

        private Object id;
        private String nick_name;
        private String login_name;
        private Object password;
        private Object salt;
        private Object update_time;
        private Object create_time;
        private Object edit_user;
        private Object role_id;
        private Object role_name;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getLogin_name() {
            return login_name;
        }

        public void setLogin_name(String login_name) {
            this.login_name = login_name;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public Object getSalt() {
            return salt;
        }

        public void setSalt(Object salt) {
            this.salt = salt;
        }

        public Object getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(Object update_time) {
            this.update_time = update_time;
        }

        public Object getCreate_time() {
            return create_time;
        }

        public void setCreate_time(Object create_time) {
            this.create_time = create_time;
        }

        public Object getEdit_user() {
            return edit_user;
        }

        public void setEdit_user(Object edit_user) {
            this.edit_user = edit_user;
        }

        public Object getRole_id() {
            return role_id;
        }

        public void setRole_id(Object role_id) {
            this.role_id = role_id;
        }

        public Object getRole_name() {
            return role_name;
        }

        public void setRole_name(Object role_name) {
            this.role_name = role_name;
        }
    }
}
