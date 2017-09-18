package com.example.android.vostrapizza.realm;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by danielbeleza on 15/09/17.
 */

public class UserProfileMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        // DynamicRealm exposes an editable schema
        RealmSchema schema = realm.getSchema();

        if(oldVersion == 0){
            schema.create("User")
                    .addField("mName", String.class)
                    .addField("mAddress", String.class);
            oldVersion++;
        }

        if(oldVersion ==1){
            schema.get("User")
                    .addField("mPhone", String.class)
                    .addField("mEmail", String.class)
                    .addField("mPassword", String.class)
                    .addField("mUsername", String.class);
        }

    }
}
