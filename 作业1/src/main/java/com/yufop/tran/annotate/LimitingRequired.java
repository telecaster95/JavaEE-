package com.yufop.tran.annotate;


import java.lang.annotation.*;

/**
 * @author iaven00
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LimitingRequired {

    boolean required() default true;

}
