package com.example.android.vostrapizza.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by danielbeleza on 14/08/17.
 */

public class Ingredient implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mIngredient);
        dest.writeInt(this.mImageResourceId);
        dest.writeInt(this.mState);
    }

    protected Ingredient(Parcel in) {
        this.mIngredient = in.readString();
        this.mImageResourceId = in.readInt();
        this.mState = in.readInt();
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}

