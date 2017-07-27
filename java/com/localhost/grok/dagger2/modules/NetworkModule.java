package com.localhost.grok.dagger2.modules;

import com.localhost.grok.dagger2.workClasses.DummySQLite;
import com.localhost.grok.dagger2.workClasses.RetrofitLoader;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by grok on 3/6/17.
 */

@Module
public class NetworkModule {


   public NetworkModule()
    {

    }


    @Provides
    @Singleton
    RetrofitLoader getNetworkLoader()
    {
        return new RetrofitLoader();
    }





}
