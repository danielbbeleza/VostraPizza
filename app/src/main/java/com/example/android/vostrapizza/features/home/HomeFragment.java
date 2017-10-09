package com.example.android.vostrapizza.features.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.vostrapizza.R;
import com.example.android.vostrapizza.activity.OrderSummary;
import com.example.android.vostrapizza.activity.PickFromMenu;
import com.example.android.vostrapizza.activity.ProjectYourPizza;
import com.example.android.vostrapizza.activity.UserProfile;
import com.example.android.vostrapizza.fragment.FragmentLoginRegister;
import com.example.android.vostrapizza.object.User;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by danielbeleza on 08/10/2017.
 */

//TODO: Get viewpager, set buttons, set opening and closing hours

public class HomeFragment extends Fragment implements HomeView {

    ViewPagerAdapter mAdapter;
    ViewPager mPager;


    RealmResults<User> realmResults;

    String openOrClosed = "Horário de Funcionamento: ";

    Realm realm;

    ImageView imageViewNavHeader;

    //Fragments
    public DrawerLayout mDrawerLayout;
    Class fragmentClass;
    NavigationView navigationView;

    @Override
    public void showViewPager() {

    }

    @Override
    public void showOpeningClosingSchedule() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Recebe info da base de dados
        realm = Realm.getDefaultInstance();

        realmResults = realm.where(User.class)
                .findAll();

        System.out.println(realmResults.toString());

        setImageViewNavHeader(realm, view);

        setOnClickListeners(view);


        //getTimeDifference();
        tvWorkingHours.setText(openOrClosed);

        mAdapter = new ViewPagerAdapter(getContext().getApplicationContext());


        mPager.setAdapter(mAdapter);


        // Define o conteudo do navigationView
        setupDrawerContent(navigationView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.badge_icon:
                Intent intent = new Intent(getContext().getApplicationContext(), OrderSummary.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.custom_menu, menu);
    }

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });
    }

    public void selectDrawerItem(MenuItem item){

        switch(item.getItemId()){
            case R.id.nav_definitions:
                mDrawerLayout.closeDrawers();
                Intent intent = new Intent(getContext().getApplicationContext(), UserProfile.class);
                startActivity(intent);
                //fragmentClass = FragmentDefinitions.class;
                break;
            case R.id.nav_favorites:
                //fragmentClass = FragmentFavorites.class;
                break;
            case R.id.nav_orders_progress:
                //fragmentClass = FragmentOrderProgress.class;
                break;
            case R.id.nav_about:
                //fragmentClass = FragmentAbout.class;
                break;
            default:
                fragmentClass = HomeActivity.class;
                break;
        }
    }

//    public void setImageViewNavHeader(Realm realm, View view){
//
//        getNavigationView(view);
//
//        realmResults = realm.where(User.class)
//                .equalTo("mUsername", FragmentLoginRegister.usernameLogin)
//                .equalTo("mPassword", FragmentLoginRegister.passwordLogin)
//                .findAll();
//
//        if(realmResults.get(0).getmPhoto() == null){
//            imageViewNavHeader.setImageResource(R.drawable.mushrooms);
//        } else if(realmResults.get(0).getmPhoto() != null) {
//            String imageUriString = realmResults.get(0).getmPhoto();
//            Uri imageUri = Uri.parse(imageUriString);
//            imageViewNavHeader.setImageURI(imageUri);
//        }
//    }

    private void setOnClickListeners(View view){

        LinearLayout createYourPizza = (LinearLayout) view.findViewById(R.id.create_pizza_linear_layout);
        createYourPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext().getApplicationContext(), ProjectYourPizza.class);
                startActivity(intent);
            }
        });

        LinearLayout pickFromMenu = (LinearLayout) view.findViewById(R.id.pick_from_menu_linear_layout);
        pickFromMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext().getApplicationContext(), PickFromMenu.class);
                startActivity(intent);
            }
        });

        LinearLayout yourOrders = (LinearLayout) view.findViewById(R.id.your_orders_linear_layout);
        yourOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // Aberto ou Fechado
        final TextView tvOpenClosed = (TextView) view.findViewById(R.id.open_closed_text_view);

        // Diferença de horas
        final TextView tvWorkingHours = (TextView) view.findViewById(R.id.time_table);

        // Conecta o objeto DrawerLayout ao layout drawer_layout
        final DrawerLayout mDrawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        // Conecta a ViewPager à ViewPager do layout
        final ViewPager mPager = (ViewPager) view.findViewById(R.id.pager);
        // Conecta o objecto navigationView ao layout navigation_view
        final NavigationView navigationView = (NavigationView) view.findViewById(R.id.navigation_view);
    }

    public void getNavigationView(View view){
        NavigationView navigationView = (NavigationView) view.findViewById(R.id.navigation_view);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header);

        // ImageView onde surge a imagem do utilizador
        imageViewNavHeader = (ImageView) headerView.findViewById(R.id.nav_header_photo_image_view);
    }


}
