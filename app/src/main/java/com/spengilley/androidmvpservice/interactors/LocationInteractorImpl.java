package com.spengilley.androidmvpservice.interactors;


import android.content.Context;
import android.content.Intent;

import com.spengilley.androidmvpservice.data.services.LocationService;

public class LocationInteractorImpl implements LocationInteractor {
    private Context context;

    public LocationInteractorImpl(Context context) {
        this.context = context;
    }


    @Override
    public void startLocationUpdates() {
        Intent intent = new Intent(context, LocationService.class);
        intent.setAction(LocationService.REQUEST_START_LOCATION_SERVICE);
        context.startService(intent);
    }

    @Override
    public void stopLocationUpdates() {
        Intent intent = new Intent(context, LocationService.class);
        intent.setAction(LocationService.REQUEST_STOP_LOCATION_SERVICE);
        context.startService(intent);
    }

}
