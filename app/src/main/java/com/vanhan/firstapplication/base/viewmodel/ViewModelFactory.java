package com.vanhan.firstapplication.base.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.vanhan.firstapplication.DashboardViewModel;
import com.vanhan.firstapplication.base.config.SharedPrefManager;
import com.vanhan.firstapplication.di.Injection;

import java.lang.ref.WeakReference;

/**
 * Custom ViewModelFactory for creating instances of ViewModels.
 * This factory extends ViewModelProvider.NewInstanceFactory.
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    // Singleton instance of the ViewModelFactory.
    private static volatile ViewModelFactory INSTANCE;

    private final SharedPrefManager sharedPrefManager;
    private final WeakReference<Context> context;

    // Private constructor to prevent instantiation.
    private ViewModelFactory(SharedPrefManager sharedPrefManager,
                             Context context) {
        this.sharedPrefManager = sharedPrefManager;
        this.context = new WeakReference<>(context);
    }

    /**
     * Returns the singleton instance of ViewModelFactory.
     *
     * @return The singleton instance of ViewModelFactory.
     */
    public static ViewModelFactory getInstance(Context context) {
        // Double-checked locking for thread safety.
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(
                            Injection.provideSharedPrefManager(context),
                            Injection.provideContext(context)
                    );
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Creates a new instance of the requested ViewModel.
     *
     * @param modelClass The class of the ViewModel to be created.
     * @param <T>        The type of the ViewModel.
     * @return A new instance of the requested ViewModel.
     * @throws IllegalArgumentException If the ViewModel class is unknown.
     */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        // Check if the requested ViewModel is assignable from BaseViewModel.
        if (modelClass.isAssignableFrom(DashboardViewModel.class)) {
            // Return an instance of BaseViewModel.
            return (T) new DashboardViewModel();
        }

        // Add additional ViewModel creation logic here if needed.

        // If the requested ViewModel class is not recognized, throw an exception.
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}