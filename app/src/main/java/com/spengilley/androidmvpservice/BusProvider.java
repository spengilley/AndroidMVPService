package com.spengilley.androidmvpservice;


import com.squareup.otto.Bus;

public class BusProvider {
    private static Bus bus = new Bus();


    public static Bus getDefault() {
        return bus;
    }


}
