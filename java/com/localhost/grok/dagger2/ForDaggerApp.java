package com.localhost.grok.dagger2;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by grok on 3/10/17.
 */
@Qualifier
@Retention(RUNTIME)
public @interface ForDaggerApp {
}