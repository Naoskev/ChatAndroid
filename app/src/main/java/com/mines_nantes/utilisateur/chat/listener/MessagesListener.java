package com.mines_nantes.utilisateur.chat.listener;

import com.mines_nantes.utilisateur.chat.model.Message;

import java.util.List;

/**
 * Created by Utilisateur on 21/01/2015.
 */
public interface MessagesListener {

    void MessageListUpdatedSuccess(List<Message> list);

    void MessageListUpdatedFail();
}
