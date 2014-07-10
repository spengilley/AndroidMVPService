package com.spengilley.androidmvpservice.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.spengilley.androidmvpservice.R;
import com.spengilley.androidmvpservice.ui.common.BaseFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import timber.log.Timber;


public class IntroFragment extends BaseFragment {
    public static final String TAG = "IntroFragment";
    private View view;
    private FragmentCallback callback;

    @InjectView(R.id.button_open_location_fragment) Button loadLocationButton;


    public static IntroFragment newInstance() {
        return new IntroFragment();
    }

    public IntroFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_intro, container, false);
        ButterKnife.inject(this, view);
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            callback = (FragmentCallback) activity;
        } catch (ClassCastException cce) {
            Timber.e("Parent Activity must implement FragmentCallback interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }


    /**
     * Public method which will enable or disable the Load Location Fragment button
     */
    public void setLoadLocationButtonState(boolean state) {
        if (loadLocationButton != null) {
            loadLocationButton.setEnabled(state);
        }
    }


    /**
     * Load LocationFragment
     */
    @OnClick(R.id.button_open_location_fragment)
    public void openLocationFragment() {
        // Tell Activity to load the LocationFragment
        callback.loadLocationFragment();
    }
}
