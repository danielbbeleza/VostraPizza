package com.example.android.vostrapizza.features.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.vostrapizza.R;
import com.example.android.vostrapizza.fragment.FragmentLoginRegister;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();

        // Recebe info da base de dados
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.frame_layout_content);

        if(fragment == null){
            fragment = new HomeFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout_content, fragment)
                    .commit();
        }

    }
}
