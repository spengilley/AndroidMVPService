package com.spengilley.androidmvpservice.data.services.common;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.spengilley.androidmvpservice.App;

import java.util.List;

import dagger.ObjectGraph;


public abstract class BaseService extends Service {
    private ObjectGraph serviceGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        serviceGraph = ((App) getApplication()).createScopedGraph(getModules().toArray());
        serviceGraph.inject(this);
    }


    protected abstract List<Object> getModules();


    
    
    
    

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
