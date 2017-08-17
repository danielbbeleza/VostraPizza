package com.example.android.vostrapizza.Objects;

/**
 * Created by danielbeleza on 14/08/17.
 */

public class Ingredient {

    private String mIngredient;
    private int mImageResourceId;
    private int mState;

    public Ingredient(String ingredient, int imageResourceId, int state){

        this.mIngredient = ingredient;
        this.mImageResourceId = imageResourceId;
        this.mState = state;
    }

    public String getIngredient(){

        return this.mIngredient;
    }

    public int getImageResourceId(){

        return this.mImageResourceId;
    }

    public int getState(){
        return this.mState;
    }

    public void setState(int state){

        this.mState = state;
    }

}

