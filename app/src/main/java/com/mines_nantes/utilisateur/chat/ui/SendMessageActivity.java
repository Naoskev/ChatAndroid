package com.mines_nantes.utilisateur.chat.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.mines_nantes.utilisateur.chat.R;
import com.mines_nantes.utilisateur.chat.listener.SendingMessageListener;
import com.mines_nantes.utilisateur.chat.task.SendMessageTask;

public class SendMessageActivity extends Activity implements View.OnClickListener, SendingMessageListener {

    private EditText messageToSend;
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        messageToSend = (EditText) findViewById(R.id.sendingText);

        sp = getSharedPreferences(LoginActivity.PREFERENCES, MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button_send_message:
                sendMessage();
                break;
        }
    }

    private void sendMessage(){
        String msg = messageToSend.getText().toString();

        if(msg != null && !msg.trim().isEmpty()) {
            new SendMessageTask(this).execute(
                    sp.getString(LoginActivity.PREF_LOGIN, ""),
                    sp.getString(LoginActivity.PREF_PASSWORD, ""),
                    msg);
        }
    }

    @Override
    public void messageSendingSuccess() {

    }

    @Override
    public void messageSendingFailure() {

    }
}
