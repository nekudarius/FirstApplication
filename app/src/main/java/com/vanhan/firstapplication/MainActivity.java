package com.vanhan.firstapplication;

import com.vanhan.firstapplication.base.BaseActivity;
import com.vanhan.firstapplication.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    public ActivityMainBinding binding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }


    @Override
    public void onInitViews() {

    }

    @Override
    public void onInitObservers() {

    }
}