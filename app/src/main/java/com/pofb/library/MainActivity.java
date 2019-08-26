package com.pofb.library;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.pofb.library.model.Book;
import com.pofb.library.model.User;
import com.pofb.library.tasks.CheckLentBooksTask;
import com.pofb.library.tasks.LoginTask;

import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.protocol.HttpContext;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BookListAdapter.ItemClickListener {
    FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);



        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ;
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        transaction = getSupportFragmentManager().beginTransaction();
        MainFragment mainFragment = new MainFragment();
        transaction.replace(R.id.main_fragment_container, mainFragment);
        transaction.commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent it = new Intent(this, SettingsActivity.class);
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //TODO Ver o que irá ser feito com esse código
    public void onBookClick(Book b) {


    }

    @Override
    public void onClick(View view, Book b) {

        transaction = getSupportFragmentManager().beginTransaction();
        BookDetailsFragment bookDetailsFragment = BookDetailsFragment.newInstance(b);
        transaction.replace(R.id.main_fragment_container, bookDetailsFragment).addToBackStack(null);

        transaction.commit();
    }

//    private class DownloadTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... urls) {
//            HttpGet request = null;
//            String content = null;
//            String contentPost = null;
//            String contentGet2 = null;
//            CookieStore cookieStore = new BasicCookieStore();
//            try {
//                HttpContext localContext = new BasicHttpContext();
//                localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
//                HttpClient client = HttpClientBuilder.create().build();
//                request = new HttpGet(urls[0]);
//
//                request.addHeader("User-Agent", "Apache HTTPClient");
//                HttpResponse response = client.execute(request, localContext);
//
//                HttpEntity entity = response.getEntity();
//                content = EntityUtils.toString(entity);
//                Log.d("hue", content);
//
//                Log.d("huee", cookieStore.getCookies().get(0).toString());
//
//                HttpPost httppost = new HttpPost("http://biblioteca.cefet-rj.br/php/login.php?iIdioma=0&iBanner=0&content=mensagens");
//
//                List<NameValuePair> params = new ArrayList<NameValuePair>(2);
//                params.add(new BasicNameValuePair("codigo", "1610631GSIS"));
//                params.add(new BasicNameValuePair("senha", "peedro21"));
//                params.add(new BasicNameValuePair("sub_login", "sim"));
//                httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
//
//                HttpResponse responsePost = client.execute(httppost, localContext);
//                HttpEntity entityPost = responsePost.getEntity();
//
//                contentPost = EntityUtils.toString(entityPost);
//                Log.d("post1", contentPost);
//                Log.d("post2", cookieStore.getCookies().get(0).toString());
//
//                HttpGet requestGet2 = new HttpGet("http://biblioteca.cefet-rj.br/index.php?modo_busca=rapida&content=circulacoes&iFiltroBib=0&iBanner=0&iEscondeMenu=0&iSomenteLegislacao=0&iIdioma=0");
//
//                requestGet2.addHeader("User-Agent", "Apache HTTPClient");
//                HttpResponse responseGet2 = client.execute(requestGet2, localContext);
//
//                HttpEntity entityGet2 = responseGet2.getEntity();
//                contentGet2 = EntityUtils.toString(entityGet2);
//                Log.d("geta", contentGet2);
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//
//                if (request != null) {
//
//                    request.releaseConnection();
//                }
//            }
//            return contentGet2;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            contentView.setText(result);
//
//        }
//    }
}
