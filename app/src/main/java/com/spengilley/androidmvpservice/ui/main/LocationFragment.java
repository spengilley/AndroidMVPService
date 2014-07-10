package com.spengilley.androidmvpservice.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spengilley.androidmvpservice.R;
import com.spengilley.androidmvpservice.ui.common.BaseFragment;
import com.spengilley.androidmvpservice.ui.main.presenters.LocationPresenter;
import com.spengilley.androidmvpservice.ui.main.views.LocationView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class LocationFragment extends BaseFragment implements LocationView {
    public static final String TAG = "LocationFragment";
    private View view;
    @Inject LocationPresenter presenter;

    @InjectView(R.id.coords)
    TextView coordsTextView;


    public static LocationFragment newInstance() {
        return new LocationFragment();
    }

    public LocationFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_location, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presenter.init(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        presenter.onStop();
    }

    /**
     * LocationView method implementations
     */
    @Override
    public void updateLocation(double latitude, double longitude) {
        if (coordsTextView != null) {
            coordsTextView.setText(String.format("latitude: %1$s longitude: %2$s", String.valueOf(latitude),
                    String.valueOf(longitude)));
        }
    }


}
