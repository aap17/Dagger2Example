package com.localhost.grok.dagger2.component;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;
import javax.inject.Singleton;

/**
 * Created by grok on 3/16/17.
 */

@Scope
@Documented
@Retention(value= RetentionPolicy.RUNTIME)
public @interface ActivityOnly
{
}
