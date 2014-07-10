package com.spengilley.androidmvpservice;

import android.app.Application;

import com.spengilley.androidmvpservice.data.DataModule;
import com.spengilley.androidmvpservice.interactors.InteractorsModule;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                App.class
        },
        includes = {
                DataModule.class,
                InteractorsModule.class
        },
        library = true
)
public class AppModule {
    private final App app;

    public AppModule(App app) {
        this.app = app;
    }


    @Provides
    @Singleton
    Application provideApplication() {
        return app;
    }


    @Provides
    @Singleton
    public Bus provideBus() {
        return new Bus();
    }

}
