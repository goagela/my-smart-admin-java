package com.example.mysabase.common.util;

import com.example.mysabase.common.domian.RequestUser;

public class SmartRequestUtil {

    private static final ThreadLocal<RequestUser> requestThreadLocal=new ThreadLocal<>();
    public static void setRequestUser(RequestUser requestUser){
        if(requestUser==null){
            return;
        }
        requestThreadLocal.set(requestUser);
    }
    public static RequestUser getRequestUser(){
        return requestThreadLocal.get();
    }

    public static Long getRequestUserId(){
        RequestUser requestUser=getRequestUser();
        return requestUser==null?null:requestUser.getUserId();
    }

    public static void remove(){requestThreadLocal.remove();}
}
