package com.example.android.vostrapizza.realm;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by danielbeleza on 15/09/17.
 *
 * Class created to intialize Realm
 */


public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Realm
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("userprofile.realm")
                .schemaVersion(2)
                .migration(new UserProfileMigration())
                .build();

        Realm.setDefaultConfiguration(config);
        Realm.getInstance(config);
    }

    @Override
    public void onTerminate() {
        Realm.getDefaultInstance().close();
        super.onTerminate();
    }
}
