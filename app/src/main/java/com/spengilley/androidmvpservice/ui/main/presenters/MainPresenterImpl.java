package com.spengilley.androidmvpservice.ui.main.presenters;


import com.spengilley.androidmvpservice.data.events.NoPlayServicesEvent;
import com.spengilley.androidmvpservice.interactors.LocationInteractor;
import com.spengilley.androidmvpservice.ui.main.views.MainView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

public class MainPresenterImpl implements MainPresenter {
    private LocationInteractor locationInteractor;
    private MainView view;
    private Bus bus;

    /**
     * Public constructor
     * Inject {@link LocationInteractor} and {@link com.squareup.otto.Bus} implementation here
     */
    @Inject
    public MainPresenterImpl(LocationInteractor locationInteractor, Bus bus) {
        this.locationInteractor = locationInteractor;
        this.bus = bus;
    }


    @Override
    public void startLocationUpdates() {
        // Register with event bus
        bus.register(this);

        // Ask for location updates to start
        locationInteractor.startLocationUpdates();
    }

    @Override
    public void stopLocationUpdates() {
        // Ask for location updates to stop
        locationInteractor.stopLocationUpdates();

        // Un register from bus
        bus.unregister(this);
    }


    /**
     * Implemented from BasePresenter interface
     */
    @Override
    public void init(MainView view) {
        this.view = view;
    }


    @Subscribe
    public void onNoPlayServicesInstalled(NoPlayServicesEvent event) {
        // Pass onto MainView
        view.handlePlayServicesConnectionErrorEvent(event);
    }
}
