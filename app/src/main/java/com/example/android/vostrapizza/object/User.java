package com.example.android.vostrapizza.object;

import io.realm.RealmObject;

/**
 * Created by danielbeleza on 15/09/17.
 */

public class User extends RealmObject {

    //TODO: Adicionar colunas à base de dados: phone, email, password, username, photo, favorite pizzas, orders

    private String        mName;
    private String        mAddress;
    private String        mPhone;
    private String        mEmail;
    private String        mPassword;
    private String        mUsername;

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
}
