package com.spengilley.androidmvpservice.ui.main.views;


import com.spengilley.androidmvpservice.data.events.NoPlayServicesEvent;

public interface MainView {

    public void handlePlayServicesConnectionErrorEvent(NoPlayServicesEvent event);

}
