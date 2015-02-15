package com.mines_nantes.utilisateur.chat.ui.activity;

import android.app.Activity;
import android.content.Intent;
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
import com.mines_nantes.utilisateur.chat.model.User;
import com.mines_nantes.utilisateur.chat.task.LoginTask;
import com.mines_nantes.utilisateur.chat.utils.SharedData;


public class LoginActivity extends Activity implements View.OnClickListener, LoginListener {

    private Button loginButton;
    private Button registerButton;
    private EditText inputLogin;
    private EditText inputPassword;
    private ProgressBar progressBar;

    private LoginTask loginTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.button_login);
        registerButton = (Button) findViewById(R.id.button_register);
        inputLogin = (EditText) findViewById(R.id.login_edit_text);
        inputPassword = (EditText) findViewById(R.id.password_edit_text);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        User user =SharedData.getInstance().getUser(this);
        if(user != null){
            inputLogin.setText(user.getLogin());
            inputPassword.setText(user.getPassword());
            login();
        }
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_login:
                TextView alertText = (TextView) findViewById(R.id.alert_text);
                if(!inputLogin.getText().toString().trim().isEmpty() && !inputPassword.getText().toString().trim().isEmpty()) {

                    alertText.setVisibility(View.GONE);
                    login();
                }
                else{
                    alertText.setVisibility(View.VISIBLE);
                 }
                break;
            case R.id.button_register:
                register();
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

        SharedData.getInstance().setUser(this, new User(login, password));

        loginTask.execute(login, password);
        Toast.makeText(this, "Connecting", Toast.LENGTH_SHORT).show();
    }

    private void register(){

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

        SharedData.getInstance().setUser(this, new User(login, password));

        loginTask.execute(login, password);
        Toast.makeText(this, "Registering", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        setBusy(false);
        Intent i = new Intent(this, DashboardActivity.class);
        startActivity(i);
    }

    @Override
    public void onLoginFail() {
        Log.i("OnLoginFail", "fail");
        setBusy(false);
        Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
    }

    private void setBusy(Boolean isBusy){

        progressBar.setVisibility(View.GONE);
    }
}
