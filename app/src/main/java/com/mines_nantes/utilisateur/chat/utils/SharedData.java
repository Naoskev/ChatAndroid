package com.mines_nantes.utilisateur.chat.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.mines_nantes.utilisateur.chat.model.User;

/**
 * Created by Utilisateur on 15/02/2015.
 */
public class SharedData {

    public static final String REGISTER = "REGISTER";
    public static final String LOGIN = "LOGIN";

    private static SharedData instance;

    private SharedData(){
    }

    public static String PREFERENCES = "preferences";
    public  static String PREF_LOGIN = "stockedUser";
    public  static String PREF_PASSWORD = "stockedPass";

    public static SharedData getInstance(){
        if(instance == null){
            instance = new SharedData();
        }
        return instance;
    }

    public void setUser(Activity activity, User user){
        SharedPreferences sp = activity.getSharedPreferences(PREFERENCES, Activity.MODE_PRIVATE);

        sp.edit().putString(PREF_LOGIN, user.getLogin())
                .putString(PREF_PASSWORD, user.getPassword())
                .commit();
    }

    public User getUser(Activity activity){
        SharedPreferences sp = activity.getSharedPreferences(PREFERENCES, Activity.MODE_PRIVATE);

        User user =null;
        if(sp.contains(PREF_LOGIN)){
            user = new User(sp.getString(PREF_LOGIN, ""),sp.getString(PREF_PASSWORD, ""));
        }
        return user;
    }
}
