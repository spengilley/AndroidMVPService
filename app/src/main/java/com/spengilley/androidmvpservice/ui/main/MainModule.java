package com.spengilley.androidmvpservice.ui.main;

import com.spengilley.androidmvpservice.AppModule;
import com.spengilley.androidmvpservice.interactors.LocationInteractor;
import com.spengilley.androidmvpservice.ui.main.presenters.LocationPresenter;
import com.spengilley.androidmvpservice.ui.main.presenters.LocationPresenterImpl;
import com.spengilley.androidmvpservice.ui.main.presenters.MainPresenter;
import com.spengilley.androidmvpservice.ui.main.presenters.MainPresenterImpl;
import com.spengilley.androidmvpservice.ui.main.views.MainView;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        addsTo = AppModule.class,
        injects = {
                MainActivity.class,
                IntroFragment.class,
                LocationFragment.class
        },
        complete = false,
        library = true
)
public class MainModule {
    private MainActivity activity;


    /**
     * Constructor
     */
    public MainModule(MainActivity activity) {
        this.activity = activity;
    }


    /**
     * Provide instance of LocationPresenter
     */
    @Provides
    @Singleton
    LocationPresenter provideLocationPresenter(Bus bus) {
        return new LocationPresenterImpl(bus);
    }


    /**
     * Provide MainView
     */
    @Provides
    @Singleton
    MainView provideMainView() {
        return (MainView) activity;
    }


    /**
     * Provide access to MainPresenter Implementation
     */
    @Provides
    @Singleton
    MainPresenter provideMainPresenter(LocationInteractor locationInteractor, Bus bus) {
        return new MainPresenterImpl(locationInteractor, bus);
    }
}
