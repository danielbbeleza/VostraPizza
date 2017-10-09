package com.example.android.vostrapizza.object;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by danielbeleza on 15/09/17.
 */

public class User extends RealmObject implements Parcelable {

    //TODO: Adicionar colunas à base de dados: phone, email, password, username, photo, favorite pizzas, orders

    private String        mName;
    private String        mAddress;
    private String        mPhone;
    private String        mEmail;
    private String        mPassword;
    private String        mUsername;
    private String        mPhoto;

    public String getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(String mPhoto) {
        this.mPhoto = mPhoto;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeString(this.mAddress);
        dest.writeString(this.mPhone);
        dest.writeString(this.mEmail);
        dest.writeString(this.mPassword);
        dest.writeString(this.mUsername);
        dest.writeString(this.mPhoto);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.mName = in.readString();
        this.mAddress = in.readString();
        this.mPhone = in.readString();
        this.mEmail = in.readString();
        this.mPassword = in.readString();
        this.mUsername = in.readString();
        this.mPhoto = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
