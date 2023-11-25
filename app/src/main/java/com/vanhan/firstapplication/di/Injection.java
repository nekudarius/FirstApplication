package com.vanhan.firstapplication.di;

import android.content.Context;
import com.vanhan.firstapplication.base.config.SharedPrefManager;

/**
 * Class for providing dependencies (Dependency Injection).
 * This class contains static methods for providing instances of certain dependencies.
 */
public class Injection {

    /**
     * Provides the application context.
     *
     * @param context The application context.
     * @return The application context.
     */
    public static Context provideContext(Context context) {
        return context;
    }

    /**
     * Provides an instance of SharedPrefManager.
     *
     * @param context The application context.
     * @return An instance of SharedPrefManager.
     */
    public static SharedPrefManager provideSharedPrefManager(Context context) {
        return SharedPrefManager.getInstance(context);
    }

}
