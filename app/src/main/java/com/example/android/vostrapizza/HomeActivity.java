package com.example.android.vostrapizza;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.vostrapizza.fragment.FragmentAbout;
import com.example.android.vostrapizza.fragment.FragmentFavorites;
import com.example.android.vostrapizza.fragment.FragmentOrderProgress;
import com.example.android.vostrapizza.object.PizzaSuggestion;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    Gson gson = new Gson();
    String pizzaCount;

    MyAdapter mAdapter;
    ViewPager mPager;

    long longTimeDifference;

    String stringTimeDifference;
    String openOrClosed = "Horário de Funcionamento: ";

    Boolean openClosedBoolean ;

    Calendar currentTime;
    Calendar opening1 = Calendar.getInstance();
    Calendar opening2 = Calendar.getInstance();
    Calendar closing1 = Calendar.getInstance();
    Calendar closing2 = Calendar.getInstance();
    Calendar timeDifference = Calendar.getInstance();


    //Fragments
    public DrawerLayout mDrawerLayout;
    public Fragment fragment = null;
    Class fragmentClass;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Aberto ou Fechado
        TextView tvOpenClosed = (TextView) findViewById(R.id.open_closed_text_view);
        tvOpenClosed.append(getBusinessTimeSituation());

        // Diferença de horas
        TextView tvWorkingHours = (TextView) findViewById(R.id.time_table);
        //getTimeDifference();
        tvWorkingHours.setText(openOrClosed);

        mAdapter = new MyAdapter(this);

        // Conecta a ViewPager à ViewPager do layout
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        // Conecta o objecto navigationView ao layout navigation_view
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        // Define o conteudo do navigationView
        setupDrawerContent(navigationView);

        // Conecta o objeto DrawerLayout ao layout drawer_layout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        // Entra na Activity - ProjectYourPizza
        LinearLayout createYourPizza = (LinearLayout) findViewById(R.id.create_pizza_linear_layout);
        createYourPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, ProjectYourPizza.class);
                startActivity(intent);
            }
        });

        // Entra na Activity - PickFromMenu
        final LinearLayout pickFromMenu = (LinearLayout) findViewById(R.id.pick_from_menu_linear_layout);
        pickFromMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* if(pickFromMenu.isSelected()){
                    pickFromMenu.setBackgroundColor(view.getResources().getColor(R.color.green_group_is_selected, null));
                }*/

                Intent intent = new Intent(HomeActivity.this, PickFromMenu.class);
                startActivity(intent);
            }
        });

        LinearLayout yourOrders = (LinearLayout) findViewById(R.id.your_orders_linear_layout);
        yourOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Intent intent = new Intent(MainActivity111.this, ProjectYourPizza.class);
                // startActivity(intent);
            }
        });

        //this.stringTimeDifference = getTimeDifference();

        getJson(); // Recebe os items que já se encontram no carrinho de compras

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.badge_icon:
                Intent intent = new Intent(HomeActivity.this, OrderSummary.class);
                startActivity(intent);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.custom_menu, menu);
        return true;
    }

    private static class MyAdapter extends PagerAdapter {

        int[] mResources = {
                R.drawable.pizza1,
                R.drawable.pizza2,
                R.drawable.pizza3,
                R.drawable.pizza4,
                R.drawable.pizza5
        };

        String[] mSuggestionPizzaIngredients = {
                "Cogumelos, Oregãos, Figo, Fiambre de Perú",
                "Mozzarela, Tomate Cherry, Salmão Fumado",
                "Rúcula, Salmão Fumado, Raspas de Lima",
                "Frango, Natas, Cogumelos",
                "Bróculos, Cenoura, Azeitonas, Feijão Verde"
        };

        Context mContext;
        LayoutInflater mLayoutInflater;

        private MyAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.fragment_pager_list, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.suggestion_image);
            imageView.setImageResource(mResources[position]);

            TextView textView = (TextView) itemView.findViewById(R.id.suggestion_text);
            textView.setText(mSuggestionPizzaIngredients[position]);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    public void getJson() {

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String json = sharedPreferences.getString("pizzaArray", null);
        Type type = new TypeToken<ArrayList<PizzaSuggestion>>() {
        }.getType();

        final ArrayList<PizzaSuggestion> arrayPizza = gson.fromJson(json, type);

        try {
            if (arrayPizza.size() > 0) {
                pizzaCount = String.valueOf(arrayPizza.size());
            }
        } catch (NullPointerException n) {
            System.out.println("ERRO: " + n.getMessage());
        }
    }

    // Add/Updates Json file with arrayList with Pizzas
    public void addPizzaJson(ArrayList<PizzaSuggestion> pizzas, String editorString) {

        // Implementa o SharedPreferences
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        // Converte o arrayPizzas em formato Json
        String json = gson.toJson(pizzas);

        // Envia dados para a "base de dados"
        editor.putString(editorString, json);
        editor.apply();
    }

    public String getBusinessTimeSituation(){

        //SimpleDateFormat parser = new SimpleDateFormat("HH:mm");

        //TODO: Corrigir diferença de tempo em que o restaurante está aberto e fechado.
        this.currentTime = Calendar.getInstance();

        Calendar openingLunch = Calendar.getInstance();
        openingLunch.set(Calendar.HOUR_OF_DAY, 12);
        openingLunch.set(Calendar.MINUTE, 0);

        Calendar closingLunch = Calendar.getInstance();
        closingLunch.set(Calendar.HOUR_OF_DAY, 15);
        closingLunch.set(Calendar.MINUTE, 0);

        Calendar openingDinner = Calendar.getInstance();
        openingDinner.set(Calendar.HOUR_OF_DAY, 19);
        closingLunch.set(Calendar.MINUTE, 0);

        Calendar closingDinner = Calendar.getInstance();
        closingDinner.set(Calendar.HOUR_OF_DAY, 22);
        closingLunch.set(Calendar.MINUTE, 0);

        System.out.println(String.valueOf(closingLunch));

        System.out.println(String.valueOf(currentTime.compareTo(openingDinner)));

        // Se estiver aberto das 12h-15h
        if(currentTime.after(openingLunch) && currentTime.before(closingLunch)){

            openClosedBoolean = true;
            openOrClosed = "Fechamos dentro de " + getTimeDifference("12:00", "15:00", openClosedBoolean) + "h";

            return " Estamos abertos! :)";

            // Se estiver aberto das 19h-22h
        } else if(currentTime.after(openingDinner) && currentTime.before(closingDinner)) {

            openClosedBoolean =true;

            openOrClosed = "Fechamos dentro de " + getTimeDifference("12:00", "19:00", openClosedBoolean) + "h";

            return " Estamos abertos! :)";

            // Se estiver fechado das 22h-12h
        } else if(currentTime.before(openingLunch)){
            openClosedBoolean = false;

            openOrClosed = "Abrimos dentro de " + getTimeDifference("22:00", "12:00", openClosedBoolean) + "h";
            return " Estamos fechados! :(";

            // Se estiver fechado das 15h-19h
        } else {
            openClosedBoolean = false;

            openOrClosed = "Abrimos dentro de " + getTimeDifference("19:00", "15:00", openClosedBoolean) + "h";
            return " Estamos fechados! :(";
        }
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
                Intent intent = new Intent(HomeActivity.this, UserProfile.class);
                startActivity(intent);
                //fragmentClass = FragmentDefinitions.class;
                break;
            case R.id.nav_favorites:
                fragmentClass = FragmentFavorites.class;
                break;
            case R.id.nav_orders_progress:
                fragmentClass = FragmentOrderProgress.class;
                break;
            case R.id.nav_about:
                fragmentClass = FragmentAbout.class;
                break;
            default:
                fragmentClass = HomeActivity.class;
                break;
        }

//        try{
//            fragment = (Fragment) fragmentClass.newInstance();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.frame_layout_content, fragment).commit();
//
//        item.setChecked(true);
//
//        setTitle(item.getTitle());
//
//        mDrawerLayout.closeDrawers();
    }





    public String getTimeDifference(String initTime, String finalTime, Boolean openOrClosed){

        // Current time
        Calendar time = Calendar.getInstance();
        int minute = time.get(Calendar.MINUTE);
        int hour = time.get(Calendar.HOUR);
        int day = time.get(Calendar.DAY_OF_MONTH);
        int month = time.get(Calendar.MONTH);
        int year = time.get(Calendar.YEAR);

        long diff;

        // Custom time format
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        String currentTime = dateFormat.format(time.getTime());

        Date openT = null;
        Date currentData = null;
        Date closeT = null;

        try{
            openT = dateFormat.parse(initTime);
            currentData = dateFormat.parse(currentTime);
            closeT = dateFormat.parse(finalTime);
        } catch (ParseException e){
            e.printStackTrace();
        }


        if (openOrClosed) {// Se estiver aberto
            diff = Math.abs(closeT.getTime() - currentData.getTime());

        } else {// Se estiver fechado
            diff = Math.abs(openT.getTime() - currentData.getTime());
        }

        long diffMinutes = diff / (1000 * 60) % 60;
        long diffHours = diff / (60 * 60 * 1000);

        return String.valueOf(diffHours) + ":" + String.valueOf(diffMinutes);
    }
}
