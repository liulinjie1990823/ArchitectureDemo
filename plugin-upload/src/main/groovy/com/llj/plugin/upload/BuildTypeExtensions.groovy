package com.llj.plugin.upload

 class BuildTypeExtensions {
    String name
    String pgyApiKey
    String pgyUserKey

    public BuildTypeExtensions(String name) {
        this.name = name
        println "BuildTypeExtensions name = " + name
    }


     @Override
     public String toString() {
         return "BuildTypeExtensions{" +
                 "name='" + name + '\'' +
                 ", pgyApiKey='" + pgyApiKey + '\'' +
                 ", pgyUserKey='" + pgyUserKey + '\'' +
                 '}';
     }
 }