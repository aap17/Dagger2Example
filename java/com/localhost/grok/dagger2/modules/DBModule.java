package com.localhost.grok.dagger2.modules;



import android.app.Application;
import android.content.Context;

import com.localhost.grok.dagger2.DaggerApp;
import com.localhost.grok.dagger2.ForDaggerApp;
import com.localhost.grok.dagger2.component.ActivityOnly;
import com.localhost.grok.dagger2.workClasses.DummySQLite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by grok on 3/6/17.
 */
@Module
public class DBModule {

    DaggerApp daggerApp;
    public DBModule(DaggerApp daggerApp)
    {
        this.daggerApp=daggerApp;
    }

    @Provides
    @Singleton
    public DummySQLite getDummySQLite()
    {
        return new DummySQLite(daggerApp.getApplicationContext());
    }






}
