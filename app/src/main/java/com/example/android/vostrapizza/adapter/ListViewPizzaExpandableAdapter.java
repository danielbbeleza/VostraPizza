package com.example.android.vostrapizza.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.vostrapizza.object.PizzaSuggestion;
import com.example.android.vostrapizza.*;

import android.widget.Toolbar;

import java.util.HashMap;
import java.util.List;

/**
 * Created by danielbeleza on 14/08/17.
 */

public class ListViewPizzaExpandableAdapter extends BaseExpandableListAdapter {

    private List<String> mLstGroups;
    private HashMap<String, PizzaSuggestion> mListItensGrupos;
    private Context mContext;

    private String ingredientsSuggestion = null;

    public ListViewPizzaExpandableAdapter(Context context, List<String> groups, HashMap<String, PizzaSuggestion> lstPizzas){

        this.mContext = context;
        this.mLstGroups = groups;
        this.mListItensGrupos = lstPizzas;
    }

    @Override
    public int getGroupCount() {
        // Retorna a quantidade de grupos
        return mLstGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // Retorna a quantidade de itens de um grupo.
        // Teremos apenas um elemento por grupo
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        // Retorna um grupo
        return mLstGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // Retorna um item do grupo
        return mListItensGrupos.get(getGroup(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        // Retorna o id do grupo, porém como nesse exemplo
        // o grupo não possui um id específico, o retorno
        // será o próprio groupPosition
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // Retorna o id do item do grupo, porém como nesse exemplo
        // o item do grupo não possui um id específico, o retorno
        // será o próprio childPosition
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        // Retorna se os ids são específicos (únicos para cada
        // grupo ou item) ou relativos
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // Retorna se o subitem (item do grupo) é selecionável
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        // Cria os itens principais (grupos)

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.group_expandable_list, null);
        }

        // Efeito de sombra. De outra forma a barra que divide os groups aparecia na mesma
        // mas com a cor de background da childView
        View view = (View) convertView.findViewById(R.id.shadow_group_view);
        Toolbar  toolbar = (Toolbar) convertView.findViewById(R.id.toolbar_pick_from_menu);

        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.group_linear_layout);
        if (isExpanded) {
            view.setVisibility(View.VISIBLE);
            linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.green_group_is_selected, null));

        } else {
            view.setVisibility(View.GONE);
            linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.green_suggestion_text, null));
        }


        // LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.update_order_bottom_bar);

        // Pizza correspondente a cada posição
        // Foi utilizado o getChild() com o segundo parâmetro = 0, porque cada grupo só tem 1 item.
        PizzaSuggestion pizzaSuggestion = (PizzaSuggestion) getChild(groupPosition, 0);

        // Define a imageView do grupo
        ImageView ivGrupo = (ImageView) convertView.findViewById(R.id.image_expandable_group);
        ivGrupo.setImageResource(pizzaSuggestion.getImageResourceId());

        TextView tvGrupo = (TextView) convertView.findViewById(R.id.text_view_expandable_group);
        tvGrupo.setText((String) getGroup(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        // cria os subitens (itens dos grupos)

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child_expandable_list, null);
        }

        PizzaSuggestion pizzaSuggestion = (PizzaSuggestion) getChild(groupPosition, childPosition);

        // Descrição de Ingredientes
        TextView textViewIngredients = (TextView) convertView.findViewById(R.id.text_view_expandable_child);
        this.ingredientsSuggestion = convertToString(pizzaSuggestion.getIngredientsSuggestion());
        textViewIngredients.setText(this.ingredientsSuggestion);

        // Tempo de Produção de pizza
        TextView textViewTime = (TextView) convertView.findViewById(R.id.waiting_time_text_view);
        String productionTime = String.valueOf(pizzaSuggestion.getProducingTime()) + " minutos";
        textViewTime.setText(productionTime);

        return convertView;
    }

    // Converte uma List<String> em String, separando cada elemento por virgulas
    private String convertToString(List<String> ingredients){

        this.ingredientsSuggestion = null;

        for (int i = 0; i<ingredients.size() ; i++){
            if(i == 0){
                this.ingredientsSuggestion = ingredients.get(i);
            } else {
                this.ingredientsSuggestion = this.ingredientsSuggestion + ", " + ingredients.get(i);
            }
        }

        return this.ingredientsSuggestion;
    }


}
