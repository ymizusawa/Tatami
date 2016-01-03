package jp.yoshi_misa_kae.tatami.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Set;

public class SPUtils {

    private static SharedPreferences mPreferenceManager = null;

    public static void init(Context context) {
        mPreferenceManager = PreferenceManager
                .getDefaultSharedPreferences(context);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return mPreferenceManager.getBoolean(key, defValue);
    }

    public static float getFloat(String key, float defValue) {
        return mPreferenceManager.getFloat(key, defValue);
    }

    public static int getInt(String key, int defValue) {
        return mPreferenceManager.getInt(key, defValue);
    }

    public static long getLong(String key, long defValue) {
        return mPreferenceManager.getLong(key, defValue);
    }

    public static String getString(String key, String defValue) {
        return mPreferenceManager.getString(key, defValue);
    }

    public static Set<String> getStringSet(String key, Set<String> defValue) {
        return mPreferenceManager.getStringSet(key, defValue);
    }

    public static double getDouble(String key, double defValue) {
        float returnValue = mPreferenceManager.getFloat(key, Float.MIN_VALUE);

        if (Float.MIN_VALUE == returnValue) {
            return defValue;
        }
        return (double) returnValue;
    }

    public static boolean putBoolean(String key, boolean defValue) {
        return mPreferenceManager.edit().putBoolean(key, defValue).commit();
    }

    public static boolean putFloat(String key, float defValue) {
        return mPreferenceManager.edit().putFloat(key, defValue).commit();
    }

    public static boolean putInt(String key, int defValue) {
        return mPreferenceManager.edit().putInt(key, defValue).commit();
    }

    public static boolean putLong(String key, long defValue) {
        return mPreferenceManager.edit().putLong(key, defValue).commit();
    }

    public static boolean putString(String key, String defValue) {
        return mPreferenceManager.edit().putString(key, defValue).commit();
    }

    public static boolean putDouble(String key, double defValue) {
        return putFloat(key, (float) defValue);
    }

    public static boolean putStringSet(String key, Set<String> defValue) {
        return mPreferenceManager.edit().putStringSet(key, defValue).commit();
    }

}
