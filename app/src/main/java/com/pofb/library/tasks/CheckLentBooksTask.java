package com.pofb.library.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.pofb.library.model.Book;
import com.pofb.library.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.protocol.HttpClientContext;
import cz.msebera.android.httpclient.impl.client.BasicCookieStore;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.BasicHttpContext;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.EntityUtils;

public class CheckLentBooksTask extends AsyncTask<HttpContext,Void, ArrayList<Book>> {
    @Override
    protected ArrayList<Book> doInBackground(HttpContext... context) {


        HttpContext localContext = context[0];

        String url = "http://biblioteca.cefet-rj.br/mobile/renovacoes.php?idioma=ptbr&acesso=web";

        HttpClient client = HttpClientBuilder.create().build();


        HttpGet request = new HttpGet(url);

        try {
            HttpResponse response = client.execute(request, localContext);
            HttpEntity entity = response.getEntity();
            Log.d("caca",EntityUtils.toString(entity));
            //todo Criar mecanismo de extração dos livros

//            if(EntityUtils.toString(entityPost).contains("Seja bem-vindo")){
//                Log.i("loginTask", "Successful Login on Biblioteca!");
//            }else{
//                Log.e("loginTask", "Login Biblioteca Failed");
//                throw new IOException("Could not be logged into Biblioteca");
//            }
        }catch(IOException e){
            //todo Melhorar esse catch
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Book> books) {
        super.onPostExecute(books);
    }
}
