package com.example.android.vostrapizza.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.vostrapizza.object.PizzaSuggestion;
import com.example.android.vostrapizza.R;

import java.util.List;

/**
 * Created by danielbeleza on 14/08/17.
 */

public class OrderSummaryAdapter extends ArrayAdapter<PizzaSuggestion> {



    public OrderSummaryAdapter(Activity context, List<PizzaSuggestion> pizzas) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, pizzas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.order_summary_list, parent, false);
        }

        PizzaSuggestion currentPizza = getItem(position);



        // Número da Pizza + Massa Seleccionada
        TextView tvPizzaNoSelectedDough = (TextView) listItemView.findViewById(R.id.pizza_number_and_dough);

        // Nome da Pizza
        String pizzaNoDough = (currentPizza.getPizzaName() + " - " + currentPizza.getSelectedDoughSuggestion());
        tvPizzaNoSelectedDough.setText(pizzaNoDough);

        // Define o preço
        TextView tvPrice = (TextView) listItemView.findViewById(R.id.price);
        int priceValue = currentPizza.getPrice();
        String priceString = String.valueOf(priceValue) + "€";
        tvPrice.setText(priceString);

        // Ingredientes de cada pizza
        TextView tvSelectedIngredients = (TextView) listItemView.findViewById(R.id.pizza_ingredients);
        String pizzaIngredients = "";

        // Quantidade
        TextView tvQuantity = (TextView) listItemView.findViewById(R.id.quantity_order_summary);
        String quantity = "Qtd: " + String.valueOf(currentPizza.getQuantity());
        tvQuantity.setText(quantity);

        for (int i = 0; i < currentPizza.getIngredientsSuggestion().size(); i++) {
            if (i == 0) {
                pizzaIngredients = currentPizza.getIngredientsSuggestion().get(i);
            } else {
                pizzaIngredients = pizzaIngredients + ", " + currentPizza.getIngredientsSuggestion().get(i);
            }

        }

        tvSelectedIngredients.setText(pizzaIngredients);


        return listItemView;
    }
}

