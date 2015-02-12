package com.mines_nantes.utilisateur.chat.task;

import android.os.AsyncTask;
import android.util.Log;

import com.mines_nantes.utilisateur.chat.listener.SendingMessageListener;
import com.mines_nantes.utilisateur.chat.model.Message;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by Utilisateur on 21/01/2015.
 */
public class SendMessageTask extends AsyncTask<String, Void, Boolean> {

    private static final String BASE_URL = "http://training.loicortola.com/parlez-vous-android/message";
    private SendingMessageListener listener;

    public SendMessageTask(SendingMessageListener listener) {
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(String... params) {

        Log.i(this.getClass().getSimpleName(), "doInBackground");

        try {
            HttpClient client = new DefaultHttpClient();
            Log.i(this.getClass().getSimpleName(), "creation client http");

            String jsonMessage = new ObjectMapper().writeValueAsString(new Message(params[0], params[2]));

            HttpPut put = new HttpPut(BASE_URL + "/" + params[0] + "/" + params[1]);
            put.setHeader("Content-type", "application/json");
            put.setEntity(new StringEntity(jsonMessage));

            HttpResponse response = client.execute(put);

            Log.i(this.getClass().getSimpleName(), response.toString());

            return response.getStatusLine().getStatusCode() == 200;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(aBoolean){
            this.listener.messageSendingSuccess();
        }
        else {
            this.listener.messageSendingFailure();
        }
    }
}
