package com.example.android.vostrapizza;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.vostrapizza.adapter.GridAdapter;
import com.example.android.vostrapizza.object.Ingredient;
import com.example.android.vostrapizza.object.PizzaSuggestion;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProjectYourPizza extends AppCompatActivity{


    public String selectedDough = "Massa Clássica";

    // Ingredientes disponiveis
    List<Ingredient> ingredients = new ArrayList<Ingredient>();

    // Lista dos ingredientes seleccionados
    List<String> ingredientOrderSummary = new ArrayList<String>();;

    // Lista das Pizzas
    List<PizzaSuggestion> arrayPizzas = null;

    List<PizzaSuggestion> arrayPizzaJson;

    int pizzaNumber=1;

    // Preço base de cada pizza, s/ ingredientes extra
    int pizzaPrice=3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_your_pizza);

        getPizzaJson("pizzaArray");

        // Indica a Numero da Pizza que está a ser criada
        TextView tvPizzaDoughTitle = (TextView) findViewById(R.id.pizza_dough_title);
        tvPizzaDoughTitle.setText(getPizzaTitleNumber());

        // Adiciona ingredientes à arrayList ingredientes
        addIngredients();


        // Botão para adicionar uma pizza e reiniciar os items seleccionados.
        Button button = (Button) findViewById(R.id.add_pizza_order_summary);
        // Botão para entrar na OrderSummary Activity
        //Button button1 = (Button) findViewById(R.id.next_button_order_summary);

        final GridAdapter gridAdapter = new GridAdapter(this, ingredients);
        final GridView gridView = (GridView) findViewById(R.id.ingredients_grid);
        gridView.setAdapter(gridAdapter);

        //Define se o item (cada ingrediente) da GridView está seleccionado
        //Se estiver seleccionado, adiciona o ingrediente ao resumo do pedido (ingredientOrderSummary) **/
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            final public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int state = ingredients.get(position).getState();
                String selectedIngredients = ingredients.get(position).getIngredient();

                //Define if items are selected or not. If an item is selected, then "unselect" them, and vice-versa
                if (state == 0) {
                    ingredients.get(position).setState(1);
                    gridAdapter.notifyDataSetChanged();

                    ingredientOrderSummary.add(selectedIngredients);

                } else {
                    ingredients.get(position).setState(0);
                    gridAdapter.notifyDataSetChanged();

                    ingredientOrderSummary.remove(selectedIngredients);
                }
            }
        });

        // ADICIONA PIZZA & REMOVE INGREDIENTES SELECCIONADOS
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ingredientOrderSummary.size() == 0) {
                    Toast.makeText(getApplicationContext(), "Seleccione pelo menos um ingrediente", Toast.LENGTH_LONG).show();
                } else {

                    // Indica o preço da pizza
                    for(int i=0; i<ingredientOrderSummary.size(); i++){
                        pizzaPrice += 1;
                    }

                    /** Cria uma nova pizza **/
                    // Adiciona a pizza à ArrayPizzas
                    //
                    // PizzaSuggestion(int imageResourceId, String pizzaName, List<String> ingredients, String selectedDough,
                    // int price, int producingTime, int quantity, int pizzaNumber)

                    String pizzaName = "VostraPizza " + String.valueOf(pizzaNumber);
                    PizzaSuggestion pizza = new PizzaSuggestion(0, pizzaName, ingredientOrderSummary,
                            selectedDough, pizzaPrice, 20, 1, pizzaNumber);

                    arrayPizzas.add(pizza);

                    // Indica o numero da pizza que está a ser feita
                    pizzaNumber = arrayPizzas.size();

                    addPizzaJson(arrayPizzas, "pizzaArray");


                    // Remove da seleção TODOS os items que foram selecionados ou não
                    for (int index = 0; index < 9; index++) {

                        //System.out.println("Index: " + index);
                        ingredients.get(index).setState(0);

                        gridAdapter.notifyDataSetChanged();
                    }
                    pizzaPrice = 3; //€
                    ingredientOrderSummary.clear();
                }

                // Updates Pizza Title Number - "Massa da Pizza: " + pizzaNumber
                String pizzaTitleNumber = getPizzaTitleNumber();
                TextView tvPizzaDoughTitle = (TextView) findViewById(R.id.pizza_dough_title);
                tvPizzaDoughTitle.setText(pizzaTitleNumber);

                Toast.makeText(ProjectYourPizza.this, "Pizza adicionada com sucesso", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onRestart(){
        super.onRestart();

        // Implementa o SharedPreferences
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Gson gson = new Gson();

        String json = sharedPreferences.getString("pizzaArray", null);
        Type type = new TypeToken<ArrayList<PizzaSuggestion>>() {
        }.getType();

        arrayPizzaJson = new ArrayList<PizzaSuggestion>();
        arrayPizzaJson = gson.fromJson(json, type);

        try {
            if (arrayPizzaJson==null) {
                arrayPizzas = new ArrayList<PizzaSuggestion>();
            } else {
                arrayPizzas = arrayPizzaJson;
            }
        } catch(NullPointerException e){
            e.printStackTrace();
        } finally {

            // Updates pizza Title Number
            TextView tvPizzaDoughTitle = (TextView) findViewById(R.id.pizza_dough_title);
            tvPizzaDoughTitle.setText(getPizzaTitleNumber());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.badge_icon:

                addPizzaJson(arrayPizzas, "pizzaArray");
                Intent intent = new Intent(ProjectYourPizza.this, OrderSummary.class);
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


    // Para seleccionar o tipo de base/massa da pizza
    public void pizzaDough(View view){

        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()){

            //Massa Clássica
            case R.id.pizza_dough_classic:
                if(checked){
                    this.selectedDough = "Massa Clássica";
                    break;
                } break;

            //Massa Integral
            case R.id.pizza_dough_wholemeal:
                if(checked){
                    this.selectedDough = "Massa Integral";
                    break;
                }break;

            //Massa sem Gluten
            case R.id.pizza_dough_glutenFree:
                if(checked){
                    this.selectedDough = "Massa sem Gluten";
                    break;
                }break;
        }
    }

    // Adiciona os ingredientes à arrayList de ingredients
    private void addIngredients(){
        if (ingredients.isEmpty()) {
            ingredients.add(new Ingredient("Cogumelos", R.drawable.mushrooms, 0));
            ingredients.add(new Ingredient("Atum", R.drawable.canned_tuna, 0));
            ingredients.add(new Ingredient("Ananás", R.drawable.pineapple, 0));
            ingredients.add(new Ingredient("Cebola", R.drawable.onion, 0));
            ingredients.add(new Ingredient("Fiambre", R.drawable.ham, 0));
            ingredients.add(new Ingredient("Espinafres", R.drawable.spinach, 0));
            ingredients.add(new Ingredient("Frango", R.drawable.chicken, 0));
            ingredients.add(new Ingredient("Pimento", R.drawable.pepper, 0));
            ingredients.add(new Ingredient("Milho", R.drawable.corn, 0));
        }
    }

    // Add/Updates Json file with arrayList with Pizzas
    public void addPizzaJson(List<PizzaSuggestion> pizzas, String editorString){

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

    // Get Json file with arrayList with Pizzas
    public List<PizzaSuggestion> getPizzaJson(String jsonKeyString){
        // Implementa o SharedPreferences
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Gson gson = new Gson();

        String json = sharedPreferences.getString(jsonKeyString, null);
        Type type = new TypeToken<List<PizzaSuggestion>>() {
        }.getType();

        arrayPizzaJson = new ArrayList<PizzaSuggestion>();
        arrayPizzaJson = gson.fromJson(json, type);

        // Se ainda não existir uma arrayList de Pizzas -> cria um novo
        try {
            if (arrayPizzaJson == null) {
                arrayPizzas = new ArrayList<PizzaSuggestion>();

                // Se já existir, igualar a nossa list à list já criada e armazenada num ficheiro json
            } else {
                arrayPizzas = arrayPizzaJson;
                //pizzaNumber = arrayPizzas.size() + 1;
            }
        } catch(NullPointerException e){
            e.printStackTrace();
        }

        return arrayPizzas;
    }

    // Get Pizza Title Number
    public String getPizzaTitleNumber(){

        pizzaNumber = arrayPizzas.size();

        return "Massa da Pizza " + String.valueOf(this.pizzaNumber + 1);
    }




}
