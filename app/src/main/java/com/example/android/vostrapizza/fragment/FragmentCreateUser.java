package com.example.android.vostrapizza.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.vostrapizza.R;
import com.example.android.vostrapizza.object.User;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.app.Activity.RESULT_OK;

/**
 * Created by danielbeleza on 18/09/17.
 */

public class FragmentCreateUser extends Fragment {

    public static final int PICK_IMAGE = 100;

    Realm realm = null;

    EditText nameET, addressET, passwordET, usernameET, phoneET, emailET;

    String userImagePath;

    User user;

    Button createProfileButton, insertUserImage;

    FragmentManager fragmentManager;
    Fragment fragment;

    View view;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            Uri imageUri = data.getData();
            userImagePath = imageUri.toString();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        fragmentManager = getFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.frame_layout_create_user);

        view = inflater.inflate(R.layout.fragment_create_user, parent, false);

        realm = Realm.getDefaultInstance();

        nameET = (EditText) view.findViewById(R.id.user_name_edit_text);
        addressET = (EditText) view.findViewById(R.id.user_address_edit_text);
        passwordET = (EditText) view.findViewById(R.id.user_password_edit_text);
        usernameET = (EditText) view.findViewById(R.id.user_username_edit_text);
        phoneET = (EditText) view.findViewById(R.id.user_phone_edit_text);
        emailET = (EditText) view.findViewById(R.id.user_email_edit_text);

        insertUserImage = (Button) view.findViewById(R.id.insert_photo_user_button);
        insertUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        createProfileButton = (Button) view.findViewById(R.id.create_new_user_button);
        createProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = new User();

                RealmResults<User> realmResults = realm.where(User.class)
                        .equalTo("mUsername", usernameET.getText().toString())
                        .findAll();
                if(realmResults.size()>0){
                    Toast.makeText(getContext(), "Username já está a ser utilizado", Toast.LENGTH_LONG).show();
                    highlightUsernameField();
                } else {
                    createUser();
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

    private void inputData(User user, String name, String address, String phone, String email, String password,
                           String username, String photo){
        user.setmName(name);
        user.setmAddress(address);
        user.setmPhone(phone);
        user.setmEmail(email);
        user.setmPassword(password);
        user.setmUsername(username);
        user.setmPhoto(photo);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void createUser(){
        // User data
        inputData(user,
                nameET.getText().toString(),
                addressET.getText().toString(),
                phoneET.getText().toString(),
                emailET.getText().toString(),
                passwordET.getText().toString(),
                usernameET.getText().toString(),
                userImagePath);

        // Create new User
        createRealmUser(user);

        fragment = new FragmentLoginRegister();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout_create_user, fragment)
                .commit();
    }

    public void highlightUsernameField(){
        TextView tvUsername = (TextView) view.findViewById(R.id.user_category_username);

        tvUsername.setHighlightColor(getResources().getColor(R.color.salmon_primary_color));
    }
}

