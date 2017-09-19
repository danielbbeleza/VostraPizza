package com.example.android.vostrapizza.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

    EditText nameET, addressET, passwordET, usernameET, phoneET, emailET;

    User user;

    Button createProfileButton;

    FragmentManager fragmentManager;
    Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        fragmentManager = getFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.frame_layout_create_user);

        View view = inflater.inflate(R.layout.fragment_create_user, parent, false);

        realm = Realm.getDefaultInstance();

        nameET = (EditText) view.findViewById(R.id.user_name_edit_text);
        addressET = (EditText) view.findViewById(R.id.user_address_edit_text);
        passwordET = (EditText) view.findViewById(R.id.user_password_edit_text);
        usernameET = (EditText) view.findViewById(R.id.user_username_edit_text);
        phoneET = (EditText) view.findViewById(R.id.user_phone_edit_text);
        emailET = (EditText) view.findViewById(R.id.user_email_edit_text);

        createProfileButton = (Button) view.findViewById(R.id.create_new_user_button);
        createProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = new User();

                // User data
                inputData(user,
                        nameET.getText().toString(),
                        addressET.getText().toString(),
                        phoneET.getText().toString(),
                        emailET.getText().toString(),
                        passwordET.getText().toString(),
                        usernameET.getText().toString());

                // Create new User
                createRealmUser(user);

                RealmResults<User> results = realm.where(User.class).findAll();

                for(User u: results){
                    System.out.println(u.getmName() + u.getmAddress());
                }

                fragment = new FragmentLoginRegister();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout_create_user, fragment)
                        .commit();

            }
        });

        return view;
    }

    private void createRealmUser(User user){

        realm.beginTransaction();
        realm.copyToRealm(user);
        realm.commitTransaction();
    }

    private void inputData(User user, String name, String address, String phone, String email, String password, String username){
        user.setmName(name);
        user.setmAddress(address);
        user.setmPhone(phone);
        user.setmEmail(email);
        user.setmPassword(password);
        user.setmUsername(username);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
