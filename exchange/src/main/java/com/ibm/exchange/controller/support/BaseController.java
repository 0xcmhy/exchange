package com.ibm.exchange.controller.support;


public class BaseController {

    ThreadLocal<String> uidLocal=new ThreadLocal<>();

    public String getUid(){
        return uidLocal.get();
    }

    public void setUid(String uid){
        uidLocal.set(uid);
    }
}
