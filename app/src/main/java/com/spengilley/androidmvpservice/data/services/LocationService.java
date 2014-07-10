package com.spengilley.androidmvpservice.data.services;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.spengilley.androidmvpservice.data.events.LocationUpdateEvent;
import com.spengilley.androidmvpservice.data.events.NoConnectionEvent;
import com.spengilley.androidmvpservice.data.events.NoPlayServicesEvent;
import com.spengilley.androidmvpservice.data.services.common.BaseService;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;


public class LocationService extends BaseService implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        LocationListener {
    @Inject Bus bus;
    private Location currentLocation;

    /**
     * Constants used by onStartCommand
     */
    public static final String REQUEST_START_LOCATION_SERVICE = "start location service";
    public static final String REQUEST_STOP_LOCATION_SERVICE = "stop location service";

    /**
     * Keep track of whether we are listening or not
     */
    public static boolean isUpdatingLocation = false;

    /**
     * Lets us know whether we are starting or stopping location services *
     */
    private boolean updatesRequested;

    /**
     * Update frequency *
     */
    private static final long LOCATION_UPDATE_FREQUENCY = 5000;

    /**
     * Location Classes *
     */
    private LocationRequest locationRequest;
    private LocationClient locationClient;


    /**
     * Service specific methods
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        // Handle requests to either start or stop this Location services
        if (intent == null)
            return 0;

        String action = intent.getAction();

        if (action.equals(REQUEST_START_LOCATION_SERVICE)) {
            // Request that location services are started
            Timber.d("Start listening to location");
            updatesRequested = true;

            if (isServicesConnected()) {
                if (locationRequest == null)
                    locationRequest = locationRequest.create();

                // Highest priority for most accurate location
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                // Frequency of updates
                locationRequest.setInterval(LOCATION_UPDATE_FREQUENCY);

                if (locationClient == null)
                    locationClient = new LocationClient(this, this, this);

                locationClient.connect();

            } else {
                Timber.d("No play services. Passing event to UI to be actioned");
                stopSelf();
            }

        } else if (action.equals(REQUEST_STOP_LOCATION_SERVICE)) {
            // Request that location services stop
            if (isUpdatingLocation)


                if (isServicesConnected()) {
                    doStopLocationListening();
                }


        } else {
            // Oops - Ignore unrecognised action
            Timber.e("Unrecognised action sent to service");
        }


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bus.register(this);
    }


    @Override
    public void onDestroy() {
        bus.unregister(this);
    }


    /**
     * BaseService method implementation
     */
    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new ServicesModule());
    }


    /**
     * Location specific method implementations
     */
    @Override
    public void onConnected(Bundle bundle) {
        if (updatesRequested) {
            locationClient.requestLocationUpdates(locationRequest, this);
            isUpdatingLocation = true;

        } else {
            locationClient.removeLocationUpdates(this);
            locationClient.disconnect();
            isUpdatingLocation = false;

            // Stop service
            stopSelf();
        }
    }

    @Override
    public void onDisconnected() {
        Timber.e("Play services disconnected. Attempting to reconnect");
        locationClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        Timber.d("Location found");

        // Keep current location - this is used in Produce method
        if (isGoodCoordinates(location.getLatitude(), location.getLongitude())) {
            currentLocation = location;
            bus.post(new LocationUpdateEvent(location.getLatitude(), location.getLongitude()));
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        bus.post(new NoConnectionEvent());
        stopSelf();
    }


    private void doStopLocationListening() {
        Timber.d("Stop listening to location");
        if (locationClient == null)
            locationClient = new LocationClient(this, this, this);

        if (locationClient.isConnected()) {
            locationClient.removeLocationUpdates(this);
            locationClient.disconnect();

        } else {
            updatesRequested = false;
            locationClient.connect();
        }
    }


    /**
     * Check if Google Play Services are connected
     */
    public boolean isServicesConnected() {

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (ConnectionResult.SUCCESS == resultCode) {
            return true;
        } else {
            bus.post(new NoPlayServicesEvent(resultCode));
            return false;
        }
    }

    /**
     * Private utility methods
     */
    private boolean isGoodCoordinates(double latitude, double longitude) {
        try {
            boolean latgood = (latitude > -180.0 && latitude < 180.0);
            boolean longood = (longitude > -180.0 && longitude < 180.0);
            return latgood && longood;

        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException npe) {
            return false;
        }
    }


    /**
     * When a subscriber registers for LocationUpdateEvent requests,
     * try and give them any current location that we have
     */
    @Produce
    public LocationUpdateEvent produceLocationUpdate() {
        if (currentLocation != null) {
            return new LocationUpdateEvent(currentLocation.getLatitude(),
                    currentLocation.getLongitude());

        } else {
            return null;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
