package com.example.android.vostrapizza.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.vostrapizza.R;
import com.example.android.vostrapizza.object.User;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by danielbeleza on 14/09/17.
 */

public class FragmentDefinitions extends Fragment {

    Realm realm = Realm.getDefaultInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        RealmResults<User> results = realm.where(User.class)
                .equalTo("mUsername", FragmentLoginRegister.usernameLogin)
                .equalTo("mPassword", FragmentLoginRegister.passwordLogin)
                .findAll();

        View view = inflater.inflate(R.layout.fragment_definitions, parent, false);

        TextView usernameTV = (TextView) view.findViewById(R.id.user_profile_username_text_view);
        TextView nameTV = (TextView) view.findViewById(R.id.user_profile_name_text_view);
        TextView addressTV = (TextView) view.findViewById(R.id.user_profile_address_text_view);
        TextView phoneTV = (TextView) view.findViewById(R.id.user_profile_phone_text_view);
        TextView emailTV = (TextView) view.findViewById(R.id.user_profile_email_text_view);
        TextView passwordTV = (TextView) view.findViewById(R.id.user_profile_password_text_view);

        usernameTV.setText(results.get(0).getmUsername());
        nameTV.setText(results.get(0).getmName());
        addressTV.setText(results.get(0).getmAddress());
        phoneTV.setText(results.get(0).getmPhone());
        emailTV.setText(results.get(0).getmEmail());
        passwordTV.setText(results.get(0).getmPassword());

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
