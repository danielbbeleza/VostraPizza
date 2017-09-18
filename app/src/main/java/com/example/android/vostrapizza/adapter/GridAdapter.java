package com.example.android.vostrapizza.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.vostrapizza.object.Ingredient;
import com.example.android.vostrapizza.R;

import java.util.List;

/**
 * Created by danielbeleza on 14/08/17.
 */

public class GridAdapter extends ArrayAdapter<Ingredient> {



    public GridAdapter(Activity context, List<Ingredient> ingredients) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, ingredients);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.ingredient_list, parent, false);
        }

        Ingredient currentIngredient = getItem(position);

        TextView textView = (TextView)listItemView.findViewById(R.id.ingredient_text_view);
        textView.setText(currentIngredient.getIngredient());

        final ImageView imageView = (ImageView) listItemView.findViewById(R.id.suggestion_image);
        imageView.setImageResource(currentIngredient.getImageResourceId());

        int state = currentIngredient.getState();


        if (state==1){
            imageView.setAlpha(0.3f);
            imageView.setSelected(false);
        } else {
            imageView.setAlpha(1f);
            imageView.setSelected(true);
        }

        return listItemView;
    }
}
