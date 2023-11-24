package com.vanhan.firstapplication.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

abstract public class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    public abstract VB binding();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding().getRoot());

        onInitViews();
        onInitObservers();
    }

    /** intended to initialize views (like findViewById, setOnClickListener, etc.). */
    public abstract void onInitViews();

    /**
     intended to initialize observers or logic related to monitoring data changes.
     example: LiveData in MVVM architecture. */
    public abstract void onInitObservers();
}
