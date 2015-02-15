package com.mines_nantes.utilisateur.chat.task;

import android.os.AsyncTask;
import android.util.Log;

import com.mines_nantes.utilisateur.chat.listener.LoginListener;
import com.mines_nantes.utilisateur.chat.utils.SharedData;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by Utilisateur on 20/01/2015.
 * Service de lié à l'activité de login
 */
public class LoginTask extends AsyncTask<String, Void, Boolean> {

    private static String BASE_URL = "http://training.loicortola.com/parlez-vous-android/";
    private static String CONNECT_URL = "connect/";
    private static String REGISTER_URL = "register/";

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

        if (params[0].equals(SharedData.LOGIN)) {
            return login(params);
        } else if (params[0].equals(SharedData.REGISTER)) {
            return register(params);
        }
        return false;
    }

    private Boolean login(String[] params) {
        try{
            HttpClient client = new DefaultHttpClient();
            Log.i(this.getClass().getSimpleName(), "creation client http");

            HttpGet get = new HttpGet(BASE_URL+ CONNECT_URL +params[1]+"/"+params[2]);
            get.setHeader("Content-type", "application/json");
            HttpResponse response = client.execute(get);
            Log.i(this.getClass().getSimpleName(), response.toString());
            return response.getStatusLine().getStatusCode() == 200;

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private Boolean register(String[] params) {
        try{
            HttpClient client = new DefaultHttpClient();
            Log.i(this.getClass().getSimpleName(), "creation client http");

            HttpPost post = new HttpPost(BASE_URL+ REGISTER_URL +params[1]+"/"+params[2]);
            post.setHeader("Content-type", "application/json");
            HttpResponse response = client.execute(post);
            Log.i(this.getClass().getSimpleName(), response.toString());
            return response.getStatusLine().getStatusCode() == 200;

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
