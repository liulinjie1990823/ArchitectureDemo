package com.llj.architecturedemo.vo;

import java.io.Serializable;

/**
 * ArchitectureDemo describe: author liulj date 2018/4/26
 */
public class Animal implements Serializable {


  /**
   * code : 0 message : data : {"client_id":197,"about_us":""} serviceTime : 1525231679
   */

  private int      code;
  private String   message;
  private DataBean data;
  private int      serviceTime;

  public static class DataBean {

    /**
     * client_id : 197 about_us :
     */

    private int    client_id;
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
