package com.llj.architecturedemo.model;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/4/26
 */
public   class Animal {


    /**
     * code : 0
     * message :
     * data : {"client_id":197,"about_us":""}
     * serviceTime : 1525231679
     */

    private int code;
    private String   message;
    private DataBean data;
    private int      serviceTime;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public static class DataBean {
        /**
         * client_id : 197
         * about_us :
         */

        private int client_id;
        private String about_us;

        public int getClient_id() {
            return client_id;
        }

        public void setClient_id(int client_id) {
            this.client_id = client_id;
        }

        public String getAbout_us() {
            return about_us;
        }

        public void setAbout_us(String about_us) {
            this.about_us = about_us;
        }
    }
}
