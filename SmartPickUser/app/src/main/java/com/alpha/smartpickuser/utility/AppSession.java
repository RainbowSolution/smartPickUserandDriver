package com.alpha.smartpickuser.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.alpha.smartpickuser.droplocationAddFragmentPkg.PlaceModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AppSession
{   private static String PREFERENCES = "ddPrference";

    public static void setBooleanPreferences(Context context, String key, boolean isCheck) {
        SharedPreferences setting = (SharedPreferences) context.getSharedPreferences(PREFERENCES, 0);
        SharedPreferences.Editor editor = setting.edit();
        editor.putBoolean(key, isCheck);
        editor.commit();

    }

    public static boolean getBooleanPreferences(Context context, String key) {
        SharedPreferences setting = (SharedPreferences) context.getSharedPreferences(PREFERENCES, 0);
        return setting.getBoolean(key, false);

    }

    public static void setStringPreferences(Context context, String key, String value) {
        try {
            SharedPreferences setting = (SharedPreferences) context.getSharedPreferences(PREFERENCES, 0);
            SharedPreferences.Editor editor = setting.edit();
            editor.putString(key, value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void saveArrayList(Context context , ArrayList<PlaceModel> list, String key){
        SharedPreferences setting = (SharedPreferences) context.getSharedPreferences(PREFERENCES, 0);
        SharedPreferences.Editor editor = setting.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();

    }

    public static ArrayList<PlaceModel> getArrayList(Context context,String key){
        SharedPreferences setting = (SharedPreferences) context.getSharedPreferences(PREFERENCES, 0);
        SharedPreferences.Editor editor = setting.edit();
        Gson gson = new Gson();
        String json = setting.getString(key, null);
        Type type = new TypeToken<ArrayList<PlaceModel>>() {}.getType();
        return gson.fromJson(json, type);
    }
    public static String getStringPreferences(Context context, String key) {
        SharedPreferences setting = (SharedPreferences) context.getSharedPreferences(PREFERENCES, 0);
        return setting.getString(key, null);

    }

    public static void removeStringPreferences(Context context, String key) {
        SharedPreferences setting = (SharedPreferences) context.getSharedPreferences(PREFERENCES, 0);
        SharedPreferences.Editor editor = setting.edit();
        editor.remove(key);
        editor.commit();

    }

    public static void setIntegerPreferences(Context context, String key, int value) {
        SharedPreferences setting = (SharedPreferences) context.getSharedPreferences(PREFERENCES, 0);
        SharedPreferences.Editor editor = setting.edit();
        editor.putInt(key, value);
        editor.commit();

    }

    public static void clearAllSharedPreferences(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }

    public static int getIntegerPreferences(Context context, String key) {
        SharedPreferences setting = (SharedPreferences) context.getSharedPreferences(PREFERENCES, 0);
        return setting.getInt(key, 0);

    }

}
