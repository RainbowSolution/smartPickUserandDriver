package com.alpha.smartpickuser.utility;

import com.alpha.smartpickuser.droplocationAddFragmentPkg.PlaceModel;

import java.util.List;

public class CustomModel {

    public interface OnCustomStateListener {
        void stateChanged();
    }

    private static CustomModel mInstance;
    private OnCustomStateListener mListener;
    private List<PlaceModel> mState;

    private CustomModel() {}

    public static CustomModel getInstance() {
        if(mInstance == null) {
            mInstance = new CustomModel();
        }
        return mInstance;
    }

    public void setListener(OnCustomStateListener listener) {
        mListener = listener;
    }

    public void changeState(List<PlaceModel> state) {
        if(mListener != null) {
            mState = state;
            notifyStateChange();
        }
    }

    public List<PlaceModel> getState() {
        return mState;
    }

    private void notifyStateChange() {
        mListener.stateChanged();
    }

}
