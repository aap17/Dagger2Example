package com.localhost.grok.dagger2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


import com.localhost.grok.dagger2.View.CustomAdapter;
import com.localhost.grok.dagger2.workClasses.ContactJson;
import com.localhost.grok.dagger2.workClasses.DummySQLite;
import com.localhost.grok.dagger2.workClasses.RetrofitLoader;

import java.util.List;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Inject
    DummySQLite dummySQLite;

    @Inject
    RetrofitLoader retrofitLoader;


    RecyclerView recyclerView;
    String url="http://nonick.coolpage.biz";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.mList);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                ContactJson json = dummySQLite.selectPerson(2);
                Log.e("MainActivity", "got info about: "+ json.getName());

            }
        });

        ((DaggerApp)getApplication()).getComponent().inject(this);

       retrofitLoader.retrofitLoad(url).enqueue(new Callback<List<ContactJson>>() {
           @Override
           public void onResponse(Response<List<ContactJson>> response, Retrofit retrofit) {

               Log.e("Retrofit", "data loaded "+ response.body().get(0).getName());
               recyclerView.setAdapter(new CustomAdapter(response.body()));
               recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,1));

           }

           @Override
           public void onFailure(Throwable t) {

           }
       });



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
