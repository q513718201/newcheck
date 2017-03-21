package com.vito.check.bean;

import java.util.List;

/**
 * Created by xk on 2017/3/16.
 */

public class Device {


    /**
     * content : [{"id":"fc2cdfa4a61a11e6a0c8286ed488c66d","t_id":"LZD0061","soft_version":"V1.0","update_time":"2016-11-24 17:35:47","description":"甘肃省兰州市七里河区晏家坪省建筑职业技术学院体育馆","create_time":"2016-11-09 09:21:53","key_t_id":"YTJ00009|92868022","lat":"36.05637200","lng":"103.76994300","online":"0","exc_state":"1","group_id":"1","del_falg":"0","wt_state":"1","image_path":null,"user_phone":"15379058402","user_name":"王","is_use":"1"},{"id":"ffa1bad59ff711e6be7e286ed488c66d","t_id":"LZD0006","soft_version":"V1.0","update_time":"2016-11-24 17:35:47","description":"兰州市城关区东岗东路1999号。（兰州王府井百货有限公司）","create_time":"2016-11-01 13:58:16","key_t_id":"YTJ00208|92821194","lat":"36.04640700","lng":"103.87741800","online":"0","exc_state":"1","group_id":"1","del_falg":"0","wt_state":"1","image_path":null,"user_phone":"13804714703","user_name":"温总","is_use":"1"}]
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
         * id : fc2cdfa4a61a11e6a0c8286ed488c66d
         * t_id : LZD0061
         * soft_version : V1.0
         * update_time : 2016-11-24 17:35:47
         * description : 甘肃省兰州市七里河区晏家坪省建筑职业技术学院体育馆
         * create_time : 2016-11-09 09:21:53
         * key_t_id : YTJ00009|92868022
         * lat : 36.05637200
         * lng : 103.76994300
         * online : 0
         * exc_state : 1
         * group_id : 1
         * del_falg : 0
         * wt_state : 1
         * image_path : null
         * user_phone : 15379058402
         * user_name : 王
         * is_use : 1
         */

        private String id;
        private String t_id;
        private String soft_version;
        private String update_time;
        private String description;
        private String create_time;
        private String key_t_id;
        private double lat;
        private double lng;
        private String online;
        private String exc_state;
        private String group_id;
        private String del_falg;
        private String wt_state;
        private Object image_path;
        private String user_phone;
        private String user_name;
        private String is_use;

        @Override
        public String toString() {
            return "ContentBean{" +
                    "id='" + id + '\'' +
                    ", t_id='" + t_id + '\'' +
                    ", soft_version='" + soft_version + '\'' +
                    ", update_time='" + update_time + '\'' +
                    ", description='" + description + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", key_t_id='" + key_t_id + '\'' +
                    ", lat=" + lat +
                    ", lng=" + lng +
                    ", online='" + online + '\'' +
                    ", exc_state='" + exc_state + '\'' +
                    ", group_id='" + group_id + '\'' +
                    ", del_falg='" + del_falg + '\'' +
                    ", wt_state='" + wt_state + '\'' +
                    ", image_path=" + image_path +
                    ", user_phone='" + user_phone + '\'' +
                    ", user_name='" + user_name + '\'' +
                    ", is_use='" + is_use + '\'' +
                    '}';
        }

        public String getId() {
            return id;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getT_id() {
            return t_id;
        }

        public void setT_id(String t_id) {
            this.t_id = t_id;
        }

        public String getSoft_version() {
            return soft_version;
        }

        public void setSoft_version(String soft_version) {
            this.soft_version = soft_version;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getKey_t_id() {
            return key_t_id;
        }

        public void setKey_t_id(String key_t_id) {
            this.key_t_id = key_t_id;
        }


        public String getOnline() {
            return online;
        }

        public void setOnline(String online) {
            this.online = online;
        }

        public String getExc_state() {
            return exc_state;
        }

        public void setExc_state(String exc_state) {
            this.exc_state = exc_state;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public String getDel_falg() {
            return del_falg;
        }

        public void setDel_falg(String del_falg) {
            this.del_falg = del_falg;
        }

        public String getWt_state() {
            return wt_state;
        }

        public void setWt_state(String wt_state) {
            this.wt_state = wt_state;
        }

        public Object getImage_path() {
            return image_path;
        }

        public void setImage_path(Object image_path) {
            this.image_path = image_path;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getIs_use() {
            return is_use;
        }

        public void setIs_use(String is_use) {
            this.is_use = is_use;
        }
    }
}
