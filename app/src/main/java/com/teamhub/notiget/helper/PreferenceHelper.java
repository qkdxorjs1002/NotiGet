package com.teamhub.notiget.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.teamhub.notiget.R;

import java.util.Set;

public class PreferenceHelper {

    private static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreference(context)
                .edit();
    }

    public static void setPreference(Context context, String key, String value) {
        getEditor(context)
                .putString(key, value)
                .apply();
    }

    public static void setPreference(Context context, String key, int value) {
        getEditor(context)
                .putInt(key, value)
                .apply();
    }

    public static void setPreference(Context context, String key, boolean value) {
        getEditor(context)
                .putBoolean(key, value)
                .apply();
    }

    public static void setPreference(Context context, String key, Set<String> value) {
        getEditor(context)
                .putStringSet(key, value)
                .apply();
    }

    public static String getPreference(Context context, String key, String defValue) {
        return getSharedPreference(context)
                .getString(key, defValue);
    }

    public static int getPreference(Context context, String key, int defValue) {
        return getSharedPreference(context)
                .getInt(key, defValue);
    }

    public static boolean getPreference(Context context, String key, boolean defValue) {
        return getSharedPreference(context)
                .getBoolean(key, defValue);
    }

    public static Set<String> getPreference(Context context, String key, Set<String> defValue) {
        return getSharedPreference(context)
                .getStringSet(key, defValue);
    }

}
