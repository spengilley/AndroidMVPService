package com.spengilley.androidmvpservice.data.events;


public class LocationUpdateEvent {
    public double latitude, longitude;

    public LocationUpdateEvent(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
