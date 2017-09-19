package com.example.android.vostrapizza.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.android.vostrapizza.R;
import com.example.android.vostrapizza.adapter.ListViewPizzaExpandableAdapter;
import com.example.android.vostrapizza.object.PizzaSuggestion;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PickFromMenu extends AppCompatActivity {

    ArrayList<PizzaSuggestion> arrayPizzaJson;
    ArrayList<PizzaSuggestion> arrayPizzas;

    List<PizzaSuggestion> pizzas;

    List<List<String>> pizzaMenuIngredients = new ArrayList<>();

    int quantity = 1;

    PizzaSuggestion currentPizza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_from_menu);

        getPizzaJson("pizzaArray");

        final ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.pizza_expandable_list);

        //Cria Grupos
        final List<String> lstGrupos = new ArrayList<>();
        lstGrupos.add("Rossa");
        lstGrupos.add("Napoli");
        lstGrupos.add("Mediterranea");
        lstGrupos.add("Vegetariana");
        lstGrupos.add("Marrocos");

        pizzaMenuIngredients = getMenuIngredients();

        pizzas = new ArrayList<PizzaSuggestion>();
        pizzas.add(new PizzaSuggestion(R.drawable.pizza1, "Rossa", pizzaMenuIngredients.get(0), "Massa Clássica", 6, 20, 1, 0));
        pizzas.add(new PizzaSuggestion(R.drawable.pizza2, "Napoli", pizzaMenuIngredients.get(1), "Massa Clássica", 8, 20, 1, 0));
        pizzas.add(new PizzaSuggestion(R.drawable.pizza3, "Mediterranea", pizzaMenuIngredients.get(2), "Massa Clássica", 6, 20, 1,0));
        pizzas.add(new PizzaSuggestion(R.drawable.pizza4, "Vegetariana", pizzaMenuIngredients.get(3), "Massa Clássica", 7, 20, 1,0));
        pizzas.add(new PizzaSuggestion(R.drawable.pizza5, "Marrocos", pizzaMenuIngredients.get(4), "Massa Clássica", 8, 20, 1,0));


        // Cria o "relacionamento" dos grupos com seus itens
        final HashMap<String, PizzaSuggestion> lstItensGrupo = new HashMap<>();
        lstItensGrupo.put(lstGrupos.get(0), pizzas.get(0));
        lstItensGrupo.put(lstGrupos.get(1), pizzas.get(1));
        lstItensGrupo.put(lstGrupos.get(2), pizzas.get(2));
        lstItensGrupo.put(lstGrupos.get(3), pizzas.get(3));
        lstItensGrupo.put(lstGrupos.get(4), pizzas.get(4));




        // Cria um adaptador (BaseExpandableListAdapter) com os dados acima
        final ListViewPizzaExpandableAdapter adaptador = new ListViewPizzaExpandableAdapter(this, lstGrupos, lstItensGrupo);
        // Define o adaptador do ExpandableListView
        expandableListView.setAdapter(adaptador);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_pick_from_menu);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            int previousItem = -1;

            final TextView tvQuantity = (TextView) findViewById(R.id.quantity_text_view);
            @Override
            public void onGroupExpand(final int groupPosition) {

                quantity = 1;

                // Fecha o group anterior
                if (groupPosition != previousItem){
                    expandableListView.collapseGroup(previousItem);
                    previousItem = groupPosition;
                }
                toolbar.setVisibility(Toolbar.VISIBLE);

                TextView selectedPizza = (TextView) findViewById(R.id.selected_pizza_text_view);
                selectedPizza.setText(pizzas.get(groupPosition).getPizzaName());


                // Reduz a quantidade da pizza seleccionada (-1)
                TextView tvDecrement = (TextView) findViewById(R.id.decrement_text_view);
                tvDecrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        PizzaSuggestion currentPizza = pizzas.get(groupPosition);
                        if(currentPizza.getQuantity()==1){
                            // tvQuantity.setText("0");
                            System.out.println("TESTE");
                        } else {
                            quantity--;
                            tvQuantity.setText(String.valueOf(quantity));
                        }
                    }
                });

                // Aumenta a quantidade da pizza seleccionada (+1)
                TextView tvIncrement = (TextView) findViewById(R.id.increment_text_view);
                tvIncrement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        quantity++;

                         currentPizza = pizzas.get(groupPosition);

                        int updateQuantity = currentPizza.getQuantity() +1;
                        currentPizza.setQuantity(updateQuantity);

                        tvQuantity.setText(String.valueOf(quantity));

                    }
                });

                // Adiciona ao carrinho
                TextView tvAddToOrderSummary = (TextView) findViewById(R.id.update_order_summary);
                tvAddToOrderSummary.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        currentPizza = pizzas.get(groupPosition);

                        // Verifica se já existe alguma pizza criada
                        // Se sim, verifica se já existe alguma igual
                        if(arrayPizzas.size()>0) {
                            for (int i = 0; i < arrayPizzas.size(); i++) {

                                // Se sim, atualiza a quantidade e o preço
                                if (currentPizza.equals(arrayPizzas.get(i))) {
                                    int pizzaQuantity = arrayPizzas.get(i).getQuantity() + quantity;

                                    //pizzaQuantity += currentPizza.getQuantity();
                                    arrayPizzas.get(i).setQuantity(pizzaQuantity);
                                    break;
                                }

                                // Se tiver verificado todas, e não existir nenhuma igual, adiciona uma nova à lista
                                if(i == (arrayPizzas.size()-1)){
                                    arrayPizzas.add(currentPizza);
                                    break;
                                }
                            }

                            // Se não, adiciona a primeira pizza à lista
                        } else {
                            currentPizza.setQuantity(quantity);
                            arrayPizzas.add(currentPizza);
                        }

                        // Atualiza a arrayPizzas, partilhada com todas as activities
                        addPizzaJson(arrayPizzas, "pizzaArray");

                        // "Colapsa"/Fecha o grupo
                        expandableListView.collapseGroup(groupPosition);

                        tvQuantity.setText("1");

                        // Reset à quantidade de cada pizza
                        quantity = 1;
                    }
                });
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

                toolbar.setVisibility(Toolbar.GONE);
            }
        });
    }

    @Override
    protected void onRestart(){
        super.onRestart();

        this.quantity = 1;

        // Implementa o SharedPreferences
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Gson gson = new Gson();

        String json = sharedPreferences.getString("pizzaArray", null);
        Type type = new TypeToken<ArrayList<PizzaSuggestion>>() {
        }.getType();

        arrayPizzaJson = new ArrayList<>();
        arrayPizzaJson = gson.fromJson(json, type);

        try {
            if (arrayPizzaJson==null) {
                arrayPizzas = new ArrayList<>();
            } else {
                arrayPizzas = arrayPizzaJson;
            }
        } catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.badge_icon:

                addPizzaJson(arrayPizzas, "pizzaArray");
                Intent intent = new Intent(PickFromMenu.this, OrderSummary.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custom_menu, menu);
        return true;
    }

    // Adiciona/Atualiza o ficheiro JSON com a arraylist de Pizzas
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

    // Recebe o ficheiro JSON com a arraylist de Pizzas
    public ArrayList<PizzaSuggestion> getPizzaJson(String jsonKeyString) {
        // Implementa o SharedPreferences
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Gson gson = new Gson();

        String json = sharedPreferences.getString("pizzaArray", null);
        Type type = new TypeToken<ArrayList<PizzaSuggestion>>() {
        }.getType();

        arrayPizzaJson = new ArrayList<PizzaSuggestion>();
        arrayPizzaJson = gson.fromJson(json, type);

        try {
            if (arrayPizzaJson.isEmpty()) {
                arrayPizzas = new ArrayList<PizzaSuggestion>();
            } else {
                arrayPizzas = arrayPizzaJson;
                //pizzaNumber = arrayPizzas.size() + 1;
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return arrayPizzas;
    }

    public List<List<String>> getMenuIngredients(){

        List<String> rossaPizza = new ArrayList<>();
        rossaPizza.add("Frango");
        rossaPizza.add("Cogumelos");
        rossaPizza.add("Natas");

        List<String> napoliPizza = new ArrayList<>();
        napoliPizza.add("Mozzarela");
        napoliPizza.add("Tomate Cherry");
        napoliPizza.add("Salmão Fumado");

        List<String> mediterraneanPizzas = new ArrayList<>();
        mediterraneanPizzas.add("Rúcula");
        mediterraneanPizzas.add("Salmão Fumado");
        mediterraneanPizzas.add("Raspas de Lima");

        List<String> vegetarianPizza = new ArrayList<>();
        vegetarianPizza.add("Bróculos");
        vegetarianPizza.add("Cenoura");
        vegetarianPizza.add("Azeitonas");
        vegetarianPizza.add("Feijão Verde");

        List<String> moroccoPizza = new ArrayList<>();
        moroccoPizza.add("Cogumelos");
        moroccoPizza.add("Oregãos");
        moroccoPizza.add("Figo");
        moroccoPizza.add("Fiambre de Perú");

        this.pizzaMenuIngredients.add(rossaPizza);
        this.pizzaMenuIngredients.add(napoliPizza);
        this.pizzaMenuIngredients.add(mediterraneanPizzas);
        this.pizzaMenuIngredients.add(vegetarianPizza);
        this.pizzaMenuIngredients.add(moroccoPizza);

        return this.pizzaMenuIngredients;
    }
}

