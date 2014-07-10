package com.spengilley.androidmvpservice.ui.main.presenters;


import com.spengilley.androidmvpservice.ui.common.BasePresenter;
import com.spengilley.androidmvpservice.ui.main.views.MainView;

public interface MainPresenter extends BasePresenter<MainView>{

    /**
     * Request that location updates start
     */
    public void startLocationUpdates();

    /**
     * Request that any location updates stop
     */
    public void stopLocationUpdates();
}
