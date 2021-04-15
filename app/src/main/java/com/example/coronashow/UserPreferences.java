package com.example.coronashow;
import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {

    private static SharedPreferences sharedPreferences;
    private static final String PREFERENCES_PACKAGE_NAME = "com.example.coronashow";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String EMAIL_KEY = "email";
    private static final String REMEMBER_ME_KEY = "rememberMe";

    public static boolean getRememberMe(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_PACKAGE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(REMEMBER_ME_KEY, false);
    }

    public static void setRememberMe(boolean rememberKey) {
        sharedPreferences.edit().putBoolean(REMEMBER_ME_KEY, rememberKey).commit();
    }

    public static String getUsername() {
        return sharedPreferences.getString(USERNAME_KEY, "");
    }

    public static void setUsername(String name) {
        sharedPreferences.edit().putString(USERNAME_KEY, name).commit();
    }

    public static String getPassword() {
        return sharedPreferences.getString(PASSWORD_KEY, "");
    }

    public static void setPassword(String name) {
        sharedPreferences.edit().putString(PASSWORD_KEY, name).commit();
    }

    public static void setEmail(String name) {
        sharedPreferences.edit().putString(EMAIL_KEY, name).commit();
    }
}

//}
