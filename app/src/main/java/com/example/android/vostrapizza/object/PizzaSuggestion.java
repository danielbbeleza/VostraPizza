package com.example.android.vostrapizza.object;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danielbeleza on 14/08/17.
 */

public class PizzaSuggestion {

    private List<String> mIngredients = new ArrayList<String>();
    private String mSelectedDough;
    private int mPrice;
    private String mPizzaName;
    private int mProducingTime;
    private int mImageResourceId;
    private int mQuantity;
    private int mPizzaNumber;

    public PizzaSuggestion(int imageResourceId, String pizzaName, List<String> ingredients, String selectedDough,
                           int price, int producingTime, int quantity, int pizzaNumber){


        for(int i = 0; i < ingredients.size(); i++){

            mIngredients.add(ingredients.get(i));
        }

        //this.mIngredients = ingredients;
        this.mSelectedDough = selectedDough;
        this.mPrice = price;
        this.mPizzaName = pizzaName;
        this.mProducingTime = producingTime;
        this.mImageResourceId = imageResourceId;
        this.mQuantity = quantity;
        this.mPizzaNumber = pizzaNumber;
    }

    /** Ingredients **/
    public List<String> getIngredientsSuggestion(){
        return this.mIngredients;
    }

    public void setIngredientsSuggestion(List<String> ingredients){
        this.mIngredients = ingredients;
    }

    /** SelectedDough **/
    public String getSelectedDoughSuggestion(){
        return this.mSelectedDough;
    }

    public void setSelectedDoughSuggestion(String selectedDough){
        this.mSelectedDough = selectedDough;
    }

    /** Price **/

    public int getPrice(){
        return this.mPrice*this.mQuantity;
    }

    public void setPriceSuggestion(int price){
        this.mPrice = price * mQuantity;
    }

    public int getPricePerQuantity(){
        return this.mPrice*this.mQuantity;
    }

    /** Pizza Name **/
    public String getPizzaName(){
        return this.mPizzaName;
    }

    public void setPizzaName(String pizzaName){
        this.mPizzaName = pizzaName;
    }

    /** Producing Time **/
    public String getProducingTime(){
        return String.valueOf(this.mProducingTime);
    }

    public void setProducingTime(int producingTime){
        this.mProducingTime = producingTime;
    }

    /** Pizza Image **/
    public int getImageResourceId(){
        return this.mImageResourceId;
    }

    public void setImageResourceId(int imageResourceId){
        this.mImageResourceId = imageResourceId;
    }

    /** Quantity **/
    public int getQuantity(){
        return this.mQuantity;
    }

    public void setQuantity(int quantity){
        this.mQuantity = quantity;
    }

    /** Pizza Number **/
    public int getPizzaNumber(){
        return this.mPizzaNumber;
    }

    public void setPizzaNumber(int pizzaNumber){
        this.mPizzaNumber = pizzaNumber;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this){
            return true;}

        if (!(o instanceof PizzaSuggestion)) {
            return false;
        }

        PizzaSuggestion pizzaSuggestion = (PizzaSuggestion) o;

        return pizzaSuggestion.mIngredients.equals(mIngredients) &&
                pizzaSuggestion.mSelectedDough.equals(mSelectedDough) &&
                //pizzaSuggestion.mPrice == mPrice &&
                pizzaSuggestion.mPizzaName.equals(mPizzaName) &&
                pizzaSuggestion.mProducingTime == mProducingTime &&
                pizzaSuggestion.mImageResourceId == mImageResourceId;
        //pizzaSuggestion.mQuantity == mQuantity &&
        //pizzaSuggestion.mPizzaNumber == mPizzaNumber;
    }

    //Idea from effective Java : Item 9
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + mIngredients.hashCode();
        result = 31 * result + mSelectedDough.hashCode();
        //result = 31 * result + mPrice;
        result = 31 * result + mPizzaName.hashCode();
        result = 31 * result + mProducingTime;
        result = 31 * result + mImageResourceId;
        //result = 31 * result + mQuantity;
        //result = 31 * result + mPizzaNumber;
        return result;
    }


}
