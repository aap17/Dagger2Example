package com.localhost.grok.dagger2.component;

import android.content.Context;

import com.localhost.grok.dagger2.MainActivity;
import com.localhost.grok.dagger2.modules.DBModule;
import com.localhost.grok.dagger2.modules.NetworkModule;
import com.localhost.grok.dagger2.workClasses.DummySQLite;
import com.localhost.grok.dagger2.workClasses.RetrofitLoader;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by grok on 3/6/17.
 */


@Singleton
@ActivityOnly
@Component(modules = {DBModule.class, NetworkModule.class})
public interface CustomComponent {
    void inject(MainActivity activity);

}
