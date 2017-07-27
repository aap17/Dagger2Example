package com.localhost.grok.dagger2;

import android.app.Application;

import com.localhost.grok.dagger2.component.CustomComponent;

import com.localhost.grok.dagger2.component.DaggerCustomComponent;
import com.localhost.grok.dagger2.modules.DBModule;
import com.localhost.grok.dagger2.modules.NetworkModule;




import java.util.AbstractCollection;

/**
 * Created by grok on 3/6/17.
 */

public class DaggerApp extends Application {
    CustomComponent component;


    @Override
    public void onCreate() {
        super.onCreate();
        component= DaggerCustomComponent.builder().networkModule(new NetworkModule()).dBModule(new DBModule(this))
                .build();

    }


    public CustomComponent getComponent()
    {
        return component;
    }
}
