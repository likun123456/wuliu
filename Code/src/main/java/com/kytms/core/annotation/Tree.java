package com.kytms.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** 树形注解
 * Created by nidaye on 2017/11/22.
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface Tree {
    String name() default "";
}
