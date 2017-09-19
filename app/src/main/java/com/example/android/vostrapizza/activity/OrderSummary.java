package com.example.android.vostrapizza.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.android.vostrapizza.R;
import com.example.android.vostrapizza.adapter.OrderSummaryAdapter;
import com.example.android.vostrapizza.object.PizzaSuggestion;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OrderSummary extends AppCompatActivity{

    // Define o Adapter
    OrderSummaryAdapter orderSummaryAdapter;

    // Cria a ListView
    ListView lvOrderSummary;
    List<PizzaSuggestion> arrayPizzas = new ArrayList<>();
    List<String> arrayIngredients;
    List<PizzaSuggestion> arrayPizzaJson;
    int totalPrice;
    String finalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        arrayPizzas = getPizzaJson();

        orderSummaryAdapter = new OrderSummaryAdapter(this, arrayPizzas);

        lvOrderSummary = (ListView) findViewById(R.id.order_summary_list);

        // define o adapter à ListView
        lvOrderSummary.setAdapter(orderSummaryAdapter);

        // Se a lista estiver vazia, esconder a barra com o custo total
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(arrayPizzas.size()==0) {
            toolbar.setVisibility(View.GONE);
        }


        // Procura e define uma empty view na ListView, para que só apareça quando a lista tem 0 items
        View emptyView = findViewById(R.id.empty_view);
        lvOrderSummary.setEmptyView(emptyView);

        // Remove Pizza ao clicar
        lvOrderSummary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            final public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog alertDialog = new AlertDialog.Builder(OrderSummary.this).create();
                alertDialog.setTitle("Aviso");
                alertDialog.setMessage("Tem a certeza que pretende eliminar esta pizza?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Remove a Pizza
                        arrayPizzas.remove(position);
                        orderSummaryAdapter.notifyDataSetChanged();


                        // Atualiza o numero da pizza
                        for(int i = 0; i< arrayPizzas.size(); i++){
                            arrayPizzas.get(i).setPizzaNumber(i);
                        }

                        // Adiciona arrayPizzaJson ao ficheiro Json
                        addPizzaJson(arrayPizzas);

                        // Mostra o preço total final
                        TextView tvPrice = (TextView) findViewById(R.id.final_price_text_view);
                        tvPrice.setText(getTotalPrice());

                        if(arrayPizzas.size()==0) {
                            toolbar.setVisibility(View.GONE);
                        }

                        dialog.dismiss();
                    }
                });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        TextView tvTotalPrice = (TextView) findViewById(R.id.final_price_text_view);
        tvTotalPrice.setText(getTotalPrice());

        TextView tvPayment = (TextView) findViewById(R.id.payment_text_view);
        tvPayment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle item selection
        switch (item.getItemId()) {

            // Intent intent = new Intent(OrderSummary.this, PaymentActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_menu, menu);
        return true;
    }

    @Override
    public void onRestart(){
        super.onRestart();

        arrayPizzas = getPizzaJson();

        orderSummaryAdapter = new OrderSummaryAdapter(this, arrayPizzas);

        lvOrderSummary = (ListView) findViewById(R.id.order_summary_list);

        // define o adapter à ListView
        lvOrderSummary.setAdapter(orderSummaryAdapter);


    }


    // Add/Updates Json file with arrayList with Pizzas
    public void addPizzaJson(List<PizzaSuggestion> pizzas){

        // Implementa o SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        // Converte o arrayPizzas em formato Json
        String json = gson.toJson(pizzas);

        // Envia dados para a "base de dados"
        editor.putString("pizzaArray", json);
        editor.apply();
    }

    // Get Json file with arrayList with Pizzas
    public List<PizzaSuggestion> getPizzaJson(){
        // Implementa o SharedPreferences
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Gson gson = new Gson();

        String json = sharedPreferences.getString("pizzaArray", null);
        Type type = new TypeToken<List<PizzaSuggestion>>() {}.getType();

        arrayPizzaJson = new ArrayList<PizzaSuggestion>();
        arrayPizzaJson = gson.fromJson(json, type);

        if(arrayPizzaJson.isEmpty()) {
            arrayPizzas = new ArrayList<PizzaSuggestion>();
        } else {
            arrayPizzas = arrayPizzaJson;
        }

        return arrayPizzas;
    }

    public String getTotalPrice(){

        // Atualiza o preço
        totalPrice=0;

        // Calcula o novo preço
        for(int i= 0; i<arrayPizzas.size(); i++) {
            totalPrice += arrayPizzas.get(i).getPrice();
        }

        // Devolve o valor final
        return finalPrice = String.valueOf("Total: " + totalPrice + "€");


    }

}




