package com.mines_nantes.utilisateur.chat.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mines_nantes.utilisateur.chat.R;
import com.mines_nantes.utilisateur.chat.adapter.MessageAdapter;
import com.mines_nantes.utilisateur.chat.listener.MessagesListener;
import com.mines_nantes.utilisateur.chat.listener.SendingMessageListener;
import com.mines_nantes.utilisateur.chat.model.Message;
import com.mines_nantes.utilisateur.chat.task.MessagesTask;
import com.mines_nantes.utilisateur.chat.task.SendMessageTask;

import java.util.Arrays;
import java.util.List;

public class DashboardActivity extends Activity implements MessagesListener,View.OnClickListener, SendingMessageListener {

    private EditText messageToSend;
    private SharedPreferences sp;
    private ListView listView;

    @Override
    public void MessageListUpdatedSuccess(List<Message> list) {
        listView.setAdapter(new MessageAdapter(this, list));
    }

    @Override
    public void MessageListUpdatedFail() {
        Toast.makeText(this, "Show messages fail", Toast.LENGTH_SHORT).show();
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
        refresh();
    }

    @Override
    public void messageSendingFailure() {
        refresh();
        Toast.makeText(this, "Send Message fail", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        listView = (ListView) findViewById(R.id.messages);
        messageToSend = (EditText) findViewById(R.id.sendingText);

        sp = getSharedPreferences(LoginActivity.PREFERENCES, MODE_PRIVATE);

        refresh();
    }

    private void refresh(){

        new MessagesTask(this).execute(
                sp.getString(LoginActivity.PREF_LOGIN, ""),
                sp.getString(LoginActivity.PREF_PASSWORD, ""));
    }
}
