package com.mines_nantes.utilisateur.chat.ui.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mines_nantes.utilisateur.chat.R;
import com.mines_nantes.utilisateur.chat.adapter.MessageAdapter;
import com.mines_nantes.utilisateur.chat.listener.MessagesListener;
import com.mines_nantes.utilisateur.chat.listener.SendingMessageListener;
import com.mines_nantes.utilisateur.chat.model.Message;
import com.mines_nantes.utilisateur.chat.model.User;
import com.mines_nantes.utilisateur.chat.task.MessagesTask;
import com.mines_nantes.utilisateur.chat.task.SendMessageTask;
import com.mines_nantes.utilisateur.chat.utils.SharedData;

import java.util.List;

public class DashboardActivity extends Activity implements MessagesListener,View.OnClickListener, SendingMessageListener {

    private EditText messageToSend;
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
            User user = SharedData.getInstance().getUser(this);
            new SendMessageTask(this).execute(
                    user.getLogin(),
                    user.getPassword(),
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

        Button send = (Button) findViewById(R.id.button_send_message);
        send.setOnClickListener(this);

        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void refresh(){

        User user = SharedData.getInstance().getUser(this);
        new MessagesTask(this).execute(
                user.getLogin(),
                user.getPassword());
    }
}
