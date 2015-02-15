package com.mines_nantes.utilisateur.chat.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mines_nantes.utilisateur.chat.R;
import com.mines_nantes.utilisateur.chat.listener.LoginListener;
import com.mines_nantes.utilisateur.chat.task.LoginTask;


public class LoginActivity extends Activity implements View.OnClickListener, LoginListener {

    private Button loginButton;
    private Button cancelButton;
    private EditText inputLogin;
    private EditText inputPassword;
    private ProgressBar progressBar;

    private LoginTask loginTask;

    private SharedPreferences sp;

    public static String PREFERENCES = "preferences";
    public  static String PREF_LOGIN = "stockedUser";
    public  static String PREF_PASSWORD = "stockedPass";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.button_ok);
        cancelButton = (Button) findViewById(R.id.button_cancel);
        inputLogin = (EditText) findViewById(R.id.login_edit_text);
        inputPassword = (EditText) findViewById(R.id.password_edit_text);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        sp = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        if(sp.contains(PREF_LOGIN)){
            inputLogin.setText(sp.getString(PREF_LOGIN, ""));
            inputPassword.setText(sp.getString(PREF_PASSWORD, ""));
        }
        loginButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_ok:
                TextView alertText = (TextView) findViewById(R.id.alert_text);
                if(!inputLogin.getText().toString().trim().isEmpty() && !inputPassword.getText().toString().trim().isEmpty()) {

                    alertText.setVisibility(View.GONE);
                    login();
                }
                else{
                    alertText.setVisibility(View.VISIBLE);
                 }
                break;
            case R.id.button_cancel:
                cancel();
                break;
        }
    }

    private void login(){

        if(loginTask == null){
            loginTask = new LoginTask(this);
        }else if(loginTask.getStatus().equals(AsyncTask.Status.PENDING) ||
                loginTask.getStatus().equals(AsyncTask.Status.RUNNING)){
            return;
        } else if(loginTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
            loginTask = new LoginTask(this);
        }

        String login = inputLogin.getText().toString();
        String password = inputPassword.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        sp.edit().putString(PREF_LOGIN, login)
                .putString(PREF_PASSWORD, password).commit();

        loginTask.execute(login, password);
        Toast.makeText(this, "Connecting", Toast.LENGTH_SHORT).show();
    }

    private void cancel(){

        if(loginTask == null){
            return;
        }
        if(loginTask.getStatus().equals(AsyncTask.Status.RUNNING) ||
                loginTask.getStatus().equals(AsyncTask.Status.PENDING)){
            loginTask.cancel(true);
        }
        inputLogin.setText("");
        inputPassword.setText("");
    }

    @Override
    public void onLoginSuccess() {
        Log.i("OnLoginSuccess", "success");
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, DashboardActivity.class);
        startActivity(i);
    }

    @Override
    public void onLoginFail() {
        Log.i("OnLoginFail", "fail");
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
    }
}
