package com.spengilley.androidmvpservice.data;


import com.spengilley.androidmvpservice.data.services.ServicesModule;

import dagger.Module;

@Module(
        includes = {
                ServicesModule.class
        },
        complete = false,
        library = true
)
public class DataModule {


}
