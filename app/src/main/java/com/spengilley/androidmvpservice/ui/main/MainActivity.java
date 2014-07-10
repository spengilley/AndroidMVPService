package com.spengilley.androidmvpservice.ui.main;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.spengilley.androidmvpservice.R;
import com.spengilley.androidmvpservice.data.events.NoPlayServicesEvent;
import com.spengilley.androidmvpservice.ui.common.BaseActivity;
import com.spengilley.androidmvpservice.ui.main.presenters.MainPresenter;
import com.spengilley.androidmvpservice.ui.main.views.MainView;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;


public class MainActivity extends BaseActivity implements MainView, FragmentCallback {

    @Inject
    MainPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null)
            return;

        // Load IntroFragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragment_container, IntroFragment.newInstance(), IntroFragment.TAG)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.init(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Start location updates
        presenter.startLocationUpdates();
    }


    @Override
    protected void onStop() {
        super.onStop();

        // Stop location updates
        presenter.stopLocationUpdates();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Choose what to do based on the request code
        switch (requestCode) {

            // If the request code matches the code sent in onConnectionFailed
            case CONNECTION_FAILURE_RESOLUTION_REQUEST:

                switch (resultCode) {
                    // If Google Play services resolved the problem
                    case Activity.RESULT_OK:

                        // Try again to get location updates
                        presenter.startLocationUpdates();
                        break;

                    // If any other result was returned by Google Play services
                    default:
                        // If the IntroFragment is loaded, disable the Open Location Fragment button
                        IntroFragment introFragment = (IntroFragment) getFragmentManager().
                                findFragmentByTag(IntroFragment.TAG);
                        if (introFragment != null) {
                            introFragment.setLoadLocationButtonState(false);
                        }

                        break;
                }

        }
    }


    /**
     * Inject MainModule into the Object Graph
     */
    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new MainModule(this));
    }


    @Override
    public void loadLocationFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragment_container, LocationFragment.newInstance(), LocationFragment.TAG)
                .commit();
    }


    /**
     * Google play methods
     */
    public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private void showErrorDialog(int errorCode) {

        // Get the error dialog from Google Play services
        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                errorCode,
                this,
                CONNECTION_FAILURE_RESOLUTION_REQUEST);

        // If Google Play services can provide an error dialog
        if (errorDialog != null) {

            // Create a new DialogFragment in which to show the error dialog
            ErrorDialogFragment errorFragment = new ErrorDialogFragment();

            // Set the dialog in the DialogFragment
            errorFragment.setDialog(errorDialog);

            // Show the error dialog in the DialogFragment
            errorFragment.show(getFragmentManager(), null);
        }
    }

    /**
     * MainView method implementations
     */
    @Override
    public void handlePlayServicesConnectionErrorEvent(NoPlayServicesEvent event) {
        // Attempt to handle lack of Play services
        showErrorDialog(event.resultCode);
    }

    /**
     * Define a DialogFragment to display the error dialog generated in
     * showErrorDialog.
     */
    public static class ErrorDialogFragment extends DialogFragment {

        // Global field to contain the error dialog
        private Dialog mDialog;

        /**
         * Default constructor. Sets the dialog field to null
         */
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        /**
         * Set the dialog to display
         *
         * @param dialog An error dialog
         */
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        /*
         * This method must return a Dialog to the DialogFragment.
         */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }

}
