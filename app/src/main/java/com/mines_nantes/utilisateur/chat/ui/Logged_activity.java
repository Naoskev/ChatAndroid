package com.mines_nantes.utilisateur.chat.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.mines_nantes.utilisateur.chat.R;

public class Logged_activity extends Activity {

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_activity);
        sp = getSharedPreferences(LoginActivity.PREFERENCES, MODE_PRIVATE);

        ((TextView) findViewById(R.id.pseudo)).setText("Hello "+ sp.getString(LoginActivity.PREF_LOGIN, ""));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logged_activity, menu);
        return true;
    }
}
