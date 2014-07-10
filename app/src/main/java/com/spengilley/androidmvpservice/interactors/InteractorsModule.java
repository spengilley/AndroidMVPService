package com.spengilley.androidmvpservice.interactors;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        library = true,
        complete = false
)
public class InteractorsModule {

    @Provides
    @Singleton
    public LocationInteractor provideLocationInteractor(Application app) {
        return new LocationInteractorImpl(app.getApplicationContext());
    }
}
