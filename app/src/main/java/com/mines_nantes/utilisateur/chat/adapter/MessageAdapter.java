package com.mines_nantes.utilisateur.chat.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.mines_nantes.utilisateur.chat.R;
import com.mines_nantes.utilisateur.chat.model.Message;
import com.mines_nantes.utilisateur.chat.utils.SharedData;

import java.util.List;

/**
 * Created by Utilisateur on 21/01/2015.
 */
public class MessageAdapter implements ListAdapter {

    private Context context;
    private List<Message> messageList;

    public MessageAdapter(Context context, List<Message> messages){
        this.context =context;
        this.messageList = messages;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View messageView = layoutInflater.inflate(R.layout.single_message_layout, parent, false);

        if(messageList.get(position).getLogin().equals(SharedData.getInstance().getUserLogin())){
            messageView.setBackgroundColor(Color.rgb(144,238,144));
        }

        TextView loginTextView = (TextView) messageView.findViewById(R.id.message_login);
        TextView messageTextView = (TextView) messageView.findViewById(R.id.message_text);

        loginTextView.setText("<"+messageList.get(position).getLogin()+"> : ");
        messageTextView.setText(messageList.get(position).getMessage());

        return messageView;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return messageList.isEmpty();
    }
}
