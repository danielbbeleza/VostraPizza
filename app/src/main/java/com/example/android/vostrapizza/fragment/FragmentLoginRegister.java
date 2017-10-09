package com.example.android.vostrapizza.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.vostrapizza.R;
import com.example.android.vostrapizza.features.home.HomeActivity;
import com.example.android.vostrapizza.object.User;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by danielbeleza on 18/09/17.
 */

public class FragmentLoginRegister extends Fragment{

    View view;

    Realm realm = Realm.getDefaultInstance();

    EditText usernameET;
    EditText passwordET;

    public static String usernameLogin;
    public static String passwordLogin;

    Button loginButton;
    Button registerButton;

    RealmResults<User> results;

    FragmentManager fragmentManager;
    Fragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.frame_layout_create_user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login_register, parent, false);

        usernameET = (EditText) view.findViewById(R.id.login_username_edit_text);
        passwordET = (EditText) view.findViewById(R.id.login_password_edit_text);

        // Login button is clicked
        loginButton = (Button) view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usernameLogin = usernameET.getText().toString();
                passwordLogin = passwordET.getText().toString();

                results = realm.where(User.class)
                        .equalTo("mUsername", usernameLogin)
                        .equalTo("mPassword", passwordLogin)
                        .findAll();

                try {
                    if (results.get(0).getmName()!=null && results.get(0).getmPassword()!=null) {
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), HomeActivity.class);
                        getActivity().startActivity(intent);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        // Register Button is clicked
        registerButton = (Button) view.findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new FragmentCreateUser();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout_create_user, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }


}
