package com.mines_nantes.utilisateur.chat.task;

import android.os.AsyncTask;
import android.util.Log;

import com.mines_nantes.utilisateur.chat.listener.LoginListener;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * Created by Utilisateur on 20/01/2015.
 */
public class LoginTask extends AsyncTask<String, Void, Boolean> {

    private static String[] LOGINS = {"abc", "abc"};
    private static String BASE_URL = "http://training.loicortola.com/parlez-vous-android/connect";
    private LoginListener listener;

    public LoginTask(LoginListener list){
        this.listener = list;
    }

    @Override
    protected void onPostExecute(Boolean b){
        if(b){
            listener.onLoginSuccess();
        } else{
            listener.onLoginFail();
        }
        Log.i(this.getClass().getSimpleName(), "onPost : "+b);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        Log.i(this.getClass().getSimpleName(), "doInBackground");

        try{
            HttpClient client = new DefaultHttpClient();
            Log.i(this.getClass().getSimpleName(), "creation client http");


            HttpGet get = new HttpGet(BASE_URL+"/"+params[0]+"/"+params[1]);
            get.setHeader("Content-type", "application/json");
            HttpResponse response = client.execute(get);
            Log.i(this.getClass().getSimpleName(), response.toString());
            return response.getStatusLine().getStatusCode() == 200;

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
