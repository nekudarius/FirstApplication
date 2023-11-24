package com.vanhan.firstapplication.base.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vanhan.firstapplication.base.util.SingleEvents;

public class BaseViewModel extends ViewModel {
    protected final MutableLiveData<SingleEvents<String>> _showToast = new MutableLiveData<>(); //show toast messages as SingleEvents
    public LiveData<SingleEvents<String>> showToast = _showToast; // Exposing a LiveData object to observe toast messages
}
