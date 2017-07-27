package com.localhost.grok.dagger2.workClasses;

/**
 * Created by grok on 3/6/17.
 */


import android.util.Log;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;


public class RetrofitLoader {



    public RetrofitLoader()
    {


    }


    public Call retrofitLoad(String url)
    {



            JsonInterface mainNewsInterface;

            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    //.client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            mainNewsInterface = retrofit.create(JsonInterface.class);
            Call<List<ContactJson>> call = mainNewsInterface.loadMainNews();

        return call;
    }


    private interface JsonInterface {

        @GET("/contacts.json")
        Call<List<ContactJson>> loadMainNews();

    }
}
