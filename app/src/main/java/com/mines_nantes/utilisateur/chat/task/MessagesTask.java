package com.mines_nantes.utilisateur.chat.task;

import android.os.AsyncTask;
import android.util.Log;

import com.mines_nantes.utilisateur.chat.listener.MessagesListener;
import com.mines_nantes.utilisateur.chat.model.Message;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Utilisateur on 21/01/2015.
 */
public class MessagesTask extends AsyncTask<String, Void, List<Message>> {

    private static String BASE_URL = "http://training.loicortola.com/parlez-vous-android/message";
    private MessagesListener listener;

    public MessagesTask(MessagesListener lis){
        this.listener = lis;
    }

    @Override
    protected List<Message> doInBackground(String... params) {
        Log.i(this.getClass().getSimpleName(), "doInBackground");

        try{
            HttpClient client = new DefaultHttpClient();
            Log.i(this.getClass().getSimpleName(), "creation client http");


            HttpGet get = new HttpGet(BASE_URL+"/"+params[0]+"/"+params[1]);
            get.setHeader("Content-type", "application/json");
            HttpResponse response = client.execute(get);
            Log.i(this.getClass().getSimpleName(), response.toString());

            if(response.getStatusLine().getStatusCode() == 200) {
                String jsonStr = EntityUtils.toString(response.getEntity());
                if(jsonStr != null){
                    List<Message> messageList = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    JsonFactory jsonFactory = new JsonFactory();
                    JsonParser jsonParser = jsonFactory.createJsonParser(jsonStr);
                    jsonParser.nextToken();
                    ObjectMapper objectMapper = new ObjectMapper();

                    while(jsonParser.nextToken() == JsonToken.START_OBJECT){
                        Message msg = objectMapper.readValue(jsonParser, Message.class);
                        if(msg != null && msg.getLogin() != null && !msg.getLogin().trim().isEmpty() &&
                                msg.getMessage() != null && ! msg.getMessage().trim().isEmpty()) {
                            messageList.add(msg);
                        }
                    }
                    return messageList;
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(List<Message> messageList){
        Log.i(this.getClass().getSimpleName(), "onPost : ");
        if(messageList != null && messageList.size() > 0){
            Log.i(this.getClass().getSimpleName(), "success");
            listener.MessageListUpdatedSuccess(messageList);
        } else{
            Log.i(this.getClass().getSimpleName(), "fail");
        }
    }
}
