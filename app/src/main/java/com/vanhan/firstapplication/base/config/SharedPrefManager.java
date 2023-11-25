package com.vanhan.firstapplication.base.config;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private final SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "vanhan.pref.common";
    private static final String KEY_USER_EMAIL = "useremail";
    private static final String KEY_USER_PASSWORD = "userpassword";
    private static final String KEY_USER_ID = "userid";
    private static final String KEY_USER_ROLE_ID = "userroleid";
    private static final String KEY_USER_NAME = "username";

    private SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }


}
