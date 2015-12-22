package com.example.admin.zhbj.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharePreference封装
 * Created by admin on 2015/12/14.
 */
public class PrefUtils {
    public  static final  String PREF_NAME = "config";
    public static boolean getBoolean(Context ctx,String key,boolean defaultValue){
        //判断之前有没有显示过新手引导界面
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return sp.getBoolean(key,defaultValue);
    }

    public static void setBoolean(Context ctx,String key,boolean defaultValue){
        //判断之前有没有显示过新手引导界面
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean userGuide = sp.edit().putBoolean(key,defaultValue).commit();

    }
}
