package com.llj.plugin.upload

class BuildTypeExtensions1 {
    String name
    String pgyApiKey
    String pgyUserKey
    String pgyAppKey
    String password
    String installType

    public BuildTypeExtensions1(String name) {
        this.name = name
        println "BuildTypeExtensions name = " + name
    }


    @Override
    public String toString() {
        return "BuildTypeExtensions{" +
                "name='" + name + '\'' +
                ", pgyApiKey='" + pgyApiKey + '\'' +
                ", pgyUserKey='" + pgyUserKey + '\'' +
                ", pgyAppKey='" + pgyAppKey + '\'' +
                ", password='" + password + '\'' +
                ", installType='" + installType + '\'' +
                '}';
    }
}