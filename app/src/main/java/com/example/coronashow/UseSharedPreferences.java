//package com.example.coronashow;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//public class UseSharedPreferences {
//    private static SharedPreferences sharedPreferences;
//    private static final String PREFERENCES_PACKAGE_NAME="com.example.coronashow";
//    private static final String USERNAME_KEY="username";
//    private static final String PASSWORD_KEY="password";
//    private static final String REMEMBER_ME_KEY ="rememberMe";
//
//    public static void setSharedPreferences(Context context) {
//        sharedPreferences = context.getSharedPreferences(User.PREFERENCES_PACKAGE_NAME, Context.MODE_PRIVATE);
//    }
//
//    public static String getUsernameFromSharedPreferences() {
//        return sharedPreferences.getString(USERNAME_KEY, "");
//    }
//
//    public static void setUsernameForSharedPreferences(String name) {
//        sharedPreferences.edit().putString(USERNAME_KEY, name).commit();
//    }
//
//    public static String getPasswordFromSharedPreferences() {
//        return sharedPreferences.getString(PASSWORD_KEY, "");
//    }
//
//    public static void setPasswordForSharedPreferences(String name) {
//        sharedPreferences.edit().putString(PASSWORD_KEY, name).commit();
//    }
//
//    public static boolean getRememberMeFromSharedPreferences() {
//        return sharedPreferences.getBoolean(REMEMBER_ME_KEY, false);
//    }
//
//    public static void setRememberMeForSharedPreferences(boolean rememberKey) {
//        sharedPreferences.edit().putBoolean(REMEMBER_ME_KEY, rememberKey).commit();
//    }
//}
