package com.pofb.library.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.pofb.library.model.Book;
import com.pofb.library.model.User;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
            String veryLongString= EntityUtils.toString(entity);

//            int maxLogSize = 1000;
//            for(int i = 0; i <= veryLongString.length() / maxLogSize; i++) {
//                int start = i * maxLogSize;
//                int end = (i+1) * maxLogSize;
//                end = end > veryLongString.length() ? veryLongString.length() : end;
//                Log.v("bitch", veryLongString.substring(start, end));
//            }

            Document doc = Jsoup.parse(veryLongString);
            Element body = doc.body();
            Elements divs = body.getElementsByClass("div-textoLista");
            ArrayList<Book> books = new ArrayList<>();
            for(Element e: divs){
                Book b = new Book();
                //Pega o conteudo do primeiro h3
                b.setTitle(e.select("h3").first().text());

                //Pega o conteudo do primeiro p, passa por uma regex para pegar somente numeros e converte para int
                b.setId(Integer.parseInt(e.select("p").first().text().replaceAll("\\D+","")));

                b.setOriginLibrary(e.select("a").first().text());

                b.setCirculationCode(e.getElementsByClass("botaoFechar").first().attr("href").replaceAll("\\D+",""));

                Log.d("amem",b.toString());
            }

            //todo Recuperar data circulação e criar mecanismo se não houver livros

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
