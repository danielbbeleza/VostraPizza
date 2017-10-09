package com.example.android.vostrapizza.features.home;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.vostrapizza.R;

/**
 * Created by danielbeleza on 09/10/2017.
 */

public class ViewPagerAdapter extends PagerAdapter {

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

    public ViewPagerAdapter(Context context) {
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
