package com.pofb.library.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.pofb.library.model.Book;
import com.pofb.library.model.RenewAnswer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.EntityUtils;

public class RenewBooksTask extends AsyncTask<HttpContext, Void, RenewAnswer> {

    private Book b;

    public RenewBooksTask(Book b) {
        this.b = b;
    }

    @Override
    protected RenewAnswer doInBackground(HttpContext... context) {


        HttpContext localContext = context[0];

        String url = "http://biblioteca.cefet-rj.br/mobile/renovar.php?idioma=ptbr&acesso=web&codigo_circulacao=" + b.getCirculationCode();

        HttpClient client = HttpClientBuilder.create().build();

        HttpGet request = new HttpGet(url);

        RenewAnswer renewAnswer = new RenewAnswer();
        renewAnswer.setRenewed(false);

        try {
            HttpResponse response = client.execute(request, localContext);
            HttpEntity entity = response.getEntity();
            String veryLongString = EntityUtils.toString(entity);
            Document doc = Jsoup.parse(veryLongString);

            String span = doc.select("span").first().text();


            if (span.contains("Item não renovado.")) {
                renewAnswer.setRenewed(false);
                renewAnswer.setRenewOutput(span);
                return renewAnswer;

            } else if (span.contains("Item renovado.")) {
                renewAnswer.setRenewed(true);
                renewAnswer.setRenewOutput(span);
                return renewAnswer;
            } else {
                Log.e("RenewBooksTask", "Inconsistent return of Jsoup from renovation response page");
            }


//            //TODO Criar mecanismo para verificar o html de renovação
//            Log.d("huee", EntityUtils.toString(entity));
//            if (EntityUtils.toString(entity).contains("Item não renovado. Circulação renovada ou devolvida.")) {
//                Log.i("RenewBooksTask", "Item foi renovado a um tempo atrás!");
//            } else {
//                Log.i("RenewBooksTask", "Algo não deu certo");
//            }
        } catch (IOException e) {
            //todo Melhorar esse catch
            e.printStackTrace();
        } catch (NullPointerException e) {
            Log.e("RenewBooksTask", "Page content not found by jSoup");
            e.printStackTrace();
        }
        return renewAnswer;
    }
}

