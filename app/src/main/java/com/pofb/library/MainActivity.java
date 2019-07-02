package com.pofb.library;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.pofb.library.model.User;
import com.pofb.library.tasks.CheckLentBooksTask;
import com.pofb.library.tasks.LoginTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.protocol.ClientContext;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.impl.client.BasicCookieStore;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.BasicHttpContext;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.EntityUtils;

public class MainActivity extends AppCompatActivity {
    private TextView contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        HttpContext localContext = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        contentView = (TextView) findViewById(R.id.textview);
        User user = new User("1610631GSIS", "peedro21");
        LoginTask login = new LoginTask();
        try {
            localContext = login.execute(user).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        CheckLentBooksTask check = new CheckLentBooksTask();
        check.execute(localContext);
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            HttpGet request = null;
            String content = null;
            String contentPost = null;
            String contentGet2 = null;
            CookieStore cookieStore = new BasicCookieStore();
            try {
                HttpContext localContext = new BasicHttpContext();
                localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
                HttpClient client = HttpClientBuilder.create().build();
                request = new HttpGet(urls[0]);

                request.addHeader("User-Agent", "Apache HTTPClient");
                HttpResponse response = client.execute(request, localContext);

                HttpEntity entity = response.getEntity();
                content = EntityUtils.toString(entity);
                Log.d("hue", content);

                Log.d("huee", cookieStore.getCookies().get(0).toString());

                HttpPost httppost = new HttpPost("http://biblioteca.cefet-rj.br/php/login.php?iIdioma=0&iBanner=0&content=mensagens");

                List<NameValuePair> params = new ArrayList<NameValuePair>(2);
                params.add(new BasicNameValuePair("codigo", "1610631GSIS"));
                params.add(new BasicNameValuePair("senha", "peedro21"));
                params.add(new BasicNameValuePair("sub_login", "sim"));
                httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

                HttpResponse responsePost = client.execute(httppost, localContext);
                HttpEntity entityPost = responsePost.getEntity();

                contentPost = EntityUtils.toString(entityPost);
                Log.d("post1", contentPost);
                Log.d("post2", cookieStore.getCookies().get(0).toString());

                HttpGet requestGet2 = new HttpGet("http://biblioteca.cefet-rj.br/index.php?modo_busca=rapida&content=circulacoes&iFiltroBib=0&iBanner=0&iEscondeMenu=0&iSomenteLegislacao=0&iIdioma=0");

                requestGet2.addHeader("User-Agent", "Apache HTTPClient");
                HttpResponse responseGet2 = client.execute(requestGet2, localContext);

                HttpEntity entityGet2 = responseGet2.getEntity();
                contentGet2 = EntityUtils.toString(entityGet2);
                Log.d("geta", contentGet2);


            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                if (request != null) {

                    request.releaseConnection();
                }
            }
            return contentGet2;
        }

        @Override
        protected void onPostExecute(String result) {
            contentView.setText(result);

        }
    }
}
