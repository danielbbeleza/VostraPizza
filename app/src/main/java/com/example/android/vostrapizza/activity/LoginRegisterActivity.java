package com.example.android.vostrapizza.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.vostrapizza.R;
import com.example.android.vostrapizza.fragment.FragmentLoginRegister;

public class LoginRegisterActivity extends AppCompatActivity {

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.frame_layout_create_user);

        if(fragment == null){
            fragment = new FragmentLoginRegister();
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout_create_user, fragment)
                    .commit();
        }
    }
}
