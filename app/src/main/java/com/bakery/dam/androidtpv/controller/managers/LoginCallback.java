package com.bakery.dam.androidtpv.controller.managers;


import com.bakery.dam.androidtpv.model.UserToken;

public interface LoginCallback {
    void onSuccess(UserToken userToken);
    void onFailure(Throwable t);
}
