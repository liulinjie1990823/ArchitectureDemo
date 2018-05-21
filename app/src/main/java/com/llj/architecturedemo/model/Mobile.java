package com.llj.architecturedemo.model;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/21
 */
public class Mobile {

    /**
     * id : 18889
     * phone : 1318888
     * province : 山东
     * city : 济南
     * service_provider : 中国联通
     * city_code : 0531
     * postcode : 250000
     * ret : 0
     * searchStr : 13188888888
     * operator : 中国联通
     * from : https://www.iteblog.com/api/mobile.php
     * ip : 218.108.186.200
     * ua : Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36
     */

    private String id;
    private String phone;
    private String province;
    private String city;
    private String service_provider;
    private String city_code;
    private String postcode;
    private int    ret;
    private String searchStr;
    private String operator;
    private String from;
    private String ip;
    private String ua;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getService_provider() {
        return service_provider;
    }

    public void setService_provider(String service_provider) {
        this.service_provider = service_provider;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }
}
