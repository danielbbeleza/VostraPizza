package com.example.android.vostrapizza.features.home;

/**
 * Created by danielbeleza on 09/10/2017.
 */

public interface HomeModel {

    interface callbackRealmDatabase{

        void onSuccess();

        void onError();
    }

    void getRealmData();
}
