package com.pofb.library.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.pofb.library.exceptions.GetLibraryInformationException;
import com.pofb.library.model.User;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.impl.client.BasicCookieStore;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.BasicHttpContext;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.EntityUtils;

public class LoginTask extends AsyncTask<User, Void, HttpContext> {
    User user = null;

    @Override
    protected HttpContext doInBackground(User... users) {
        user = users[0];

        HttpContext localContext = new BasicHttpContext();

        String url = "http://biblioteca.cefet-rj.br/php/login.php?iIdioma=0&iBanner=0&content=mensagens";
        CookieStore cookieStore = new BasicCookieStore();
        localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
        HttpClient client = HttpClientBuilder.create().build();


        HttpPost httppost = new HttpPost(url);

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            params.add(new BasicNameValuePair("codigo", user.getUser()));
            params.add(new BasicNameValuePair("senha", user.getPassword()));
            params.add(new BasicNameValuePair("sub_login", "sim"));
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            HttpResponse responsePost = client.execute(httppost, localContext);
            HttpEntity entityPost = responsePost.getEntity();


            if (EntityUtils.toString(entityPost).contains("Seja bem-vindo")) {
                Log.i("loginTask", "Successful Login on Biblioteca!");
            } else {
                Log.e("loginTask", "Login Biblioteca Failed");
                throw new GetLibraryInformationException("Could not be logged into Biblioteca");
            }
        } catch (IOException e) {
            e.printStackTrace();
            localContext = null;
        } finally {

            if (httppost != null) {

                httppost.releaseConnection();
            }
        }
        return localContext;
    }

    @Override
    protected void onPostExecute(HttpContext httpContext) {
        super.onPostExecute(httpContext);
    }
}
