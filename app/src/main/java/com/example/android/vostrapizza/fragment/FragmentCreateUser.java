package com.example.android.vostrapizza.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.vostrapizza.R;
import com.example.android.vostrapizza.object.User;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by danielbeleza on 18/09/17.
 */

public class FragmentCreateUser extends Fragment {

    Realm realm = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_user, parent, false);

        realm = Realm.getDefaultInstance();

        final EditText nameET = (EditText) view.findViewById(R.id.user_name_edit_text);
        final EditText addressET = (EditText) view.findViewById(R.id.user_address_edit_text);

        final Button createProfileButton = (Button) view.findViewById(R.id.create_new_user_button);
        createProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final User user = new User();

                // User data
                inputData(user, nameET.getText().toString(), addressET.getText().toString());

                // Create new User
                createRealmUser(user);

                RealmResults<User> results = realm.where(User.class).findAll();

                for(User u: results){
                    System.out.println(u.getmName() + u.getmAddress());
                }

            }
        });

        return view;
    }

    private void createRealmUser(User user){

        realm.beginTransaction();
        realm.copyToRealm(user);
        realm.commitTransaction();
    }

    private void inputData(User user, String name, String address){
        user.setmName(name);
        user.setmAddress(address);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
