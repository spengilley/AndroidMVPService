package com.spengilley.androidmvpservice.ui.common;

/**
 * Base Fragment Presenter interface which should be used when
 * An init method is required to inject view into presenter
 */
public interface BasePresenter<T> {
    void init(T view);
}
