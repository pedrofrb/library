package com.pofb.library.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.pofb.library.model.Book;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.EntityUtils;

public class RenovationBooksTask extends AsyncTask<HttpContext, Void, Book> {

    private Book b;

    public RenovationBooksTask(Book b) {
        this.b = b;
    }

    @Override
    protected Book doInBackground(HttpContext... context) {


        HttpContext localContext = context[0];

        String url = "http://biblioteca.cefet-rj.br/mobile/renovar.php?idioma=ptbr&acesso=web&codigo_circulacao=" + b.getCirculationCode();

        HttpClient client = HttpClientBuilder.create().build();

        HttpGet request = new HttpGet(url);

        try {
            HttpResponse response = client.execute(request, localContext);
            HttpEntity entity = response.getEntity();

            //TODO Criar mecanismo para verificar o html de renovação
            Log.d("huee", EntityUtils.toString(entity));
            if (EntityUtils.toString(entity).contains("Item não renovado. Circulação renovada ou devolvida.")) {
                Log.i("RenovationBooksTask", "Item foi renovado a um tempo atrás!");
            } else {
                Log.i("RenovationBooksTask", "Algo não deu certo");
            }
        } catch (IOException e) {
            //todo Melhorar esse catch
            e.printStackTrace();
        }
        return b;
    }

    @Override
    protected void onPostExecute(Book b) {
        super.onPostExecute(b);
    }
}
