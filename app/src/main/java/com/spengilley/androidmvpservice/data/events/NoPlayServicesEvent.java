package com.spengilley.androidmvpservice.data.events;


public class NoPlayServicesEvent {
   public int resultCode;

    public NoPlayServicesEvent(int resultCode) {
        this.resultCode = resultCode;
    }
}
