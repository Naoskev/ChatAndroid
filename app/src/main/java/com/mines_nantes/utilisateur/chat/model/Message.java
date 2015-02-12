package com.mines_nantes.utilisateur.chat.model;

import java.util.UUID;

/**
 * Created by Utilisateur on 21/01/2015.
 */
public class Message {
    private String message;
    private String uuid;
    private String login;

    public  Message(){}

    public Message(String login, String message) {
        this.message = message;
        this.uuid = UUID.randomUUID().toString();
        this.login = login;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
