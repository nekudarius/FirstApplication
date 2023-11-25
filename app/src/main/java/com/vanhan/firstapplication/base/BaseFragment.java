package com.vanhan.firstapplication.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;
import androidx.viewbinding.ViewBinding;

import com.vanhan.firstapplication.base.viewmodel.ViewModelFactory;

import java.util.Map;


/**
 * BaseFragment is an abstract class for all fragments in the application.
 * It provides common initialization and utilities used throughout the app.
 *
 * @param <VB> The type of ViewBinding for this fragment.
 * @param <VM> The type of ViewModel for this fragment.
 */
public abstract class BaseFragment<VB extends ViewBinding, VM extends ViewModel> extends Fragment {

    /**
     * Initializes ViewBinding for the fragment.
     *
     * @return Instance of ViewBinding for the fragment.
     */
    public abstract VB initInflater();

    /** Instance of ViewBinding used by the fragment. */
    public VB binding;

    /**
     * Initializes ViewModel for the fragment.
     *
     * @param viewModelFactory Factory to create ViewModel.
     * @return Instance of ViewModel for the fragment.
     */
    public abstract VM initViewModel(ViewModelFactory viewModelFactory);

    /** Instance of ViewModel used by the fragment. */
    public VM viewModel;

    /** Flag to indicate whether the fragment has been initialized. */
    private boolean hasInitialized = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Adding callback to handle the back button
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                onBackPressed();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    /**
     * Method to handle the back button press.
     * If there is a back stack in the NavHost, popBackStack will be called.
     * If not, the fragment will be finished.
     */
    public void onBackPressed() {
        if (!Navigation.findNavController(requireView()).popBackStack()) {
            requireActivity().finish();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Ensuring that binding is initialized only once
        if (binding == null) {
            binding = initInflater();
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel and display only if not initialized previously
        if (!hasInitialized) {
            ViewModelFactory viewModelFactory = ViewModelFactory.getInstance(getContext());
            viewModel = initViewModel(viewModelFactory);

            // Call initialization of observers and views
            onInitObservers();
            onInitViews();
        }

    }

    /**
     * Abstract method to initialize views in the fragment.
     */
    public abstract void onInitViews();

    /**
     * Abstract method to initialize observers or logic related to monitoring data changes in the fragment.
     */
    public abstract void onInitObservers();

    /**
     * Displays a toast message.
     *
     * @param message Message to be displayed in the toast.
     */ 
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Marking that the fragment has been initialized
        hasInitialized = true;
    }

    /**
     * Activity Result Launcher for permission requests.
     */
    public ActivityResultLauncher<String[]> permissionRequestLauncher
            = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
            result -> {
                for (Map.Entry<String, Boolean> granted : result.entrySet()) {
                    onRequestResult(granted);
                }
            });

    /**
     * Method that can be overridden to handle the result of permission requests.
     *
     * @param result Result of the permission request.
     */
    protected void onRequestResult(Map.Entry<String, Boolean> result) {
    }
}
