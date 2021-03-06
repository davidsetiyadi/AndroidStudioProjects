package com.neostra.presentation;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Huichao on 2016/11/24.
 */

public class SharedManager {
    private static final String SETTIING_OPTIONS = "ALBUM_FLAG";
    private static SharedPreferences sharedPreferences;
    private static SharedManager sharedManager;

    public static SharedManager getInstance(Context context) {
        synchronized (SharedManager.class) {
            if (sharedManager == null)
                sharedManager = new SharedManager(context);
        }
        return sharedManager;
    }

    private SharedManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SETTIING_OPTIONS, Context.MODE_PRIVATE);
    }

    public String put(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
        return value;
    }

    public long put(String key, Long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
        return value;
    }

    public int put(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
        return value;
    }

    public boolean put(String key, Boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
        return value;
    }

    public boolean contains(String key) {
        if (null == sharedPreferences) {
            return false;
        }
        return sharedPreferences.contains(key);
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public void remove(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public Boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public Boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public Boolean getIsSupportTiny(String key) {
        return sharedPreferences.getBoolean(key, true);
    }

    public Long getLong(String key) {
        return sharedPreferences.getLong(key, 0);
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }


}

