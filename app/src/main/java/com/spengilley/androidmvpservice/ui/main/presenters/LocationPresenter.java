package com.spengilley.androidmvpservice.ui.main.presenters;


import com.spengilley.androidmvpservice.ui.common.BaseFragmentPresenter;
import com.spengilley.androidmvpservice.ui.main.views.LocationView;

public interface LocationPresenter extends BaseFragmentPresenter<LocationView>{

    public void onStop();

}
