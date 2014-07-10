package com.spengilley.androidmvpservice.ui.main.presenters;

import com.spengilley.androidmvpservice.data.events.LocationUpdateEvent;
import com.spengilley.androidmvpservice.ui.main.views.LocationView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;


public class LocationPresenterImpl implements LocationPresenter {
    private LocationView view;
    private Bus bus;


    /**
     * Public constructor gives us an instance of {@link com.squareup.otto.Bus}
     */
    @Inject
    public LocationPresenterImpl(Bus bus) {
        this.bus = bus;
    }


    @Override
    public void onStop() {
        bus.unregister(this);
    }

    @Override
    public void init(LocationView view) {
        this.view = view;

        bus.register(this);
    }

    /**
     * Subscribe to location updates from {@link com.spengilley.androidmvpservice.data.services.LocationService}
     */
    @Subscribe
    public void onLocationUpdate(LocationUpdateEvent event) {

        view.updateLocation(event.latitude, event.longitude);
    }

}
