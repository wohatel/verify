package com.murong.val.verify;


import java.lang.annotation.*;

/**
 * using on field of a java object which is tag by @RequestBody
 * always compare whith string.length
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValStringLen {

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String msg() default "";

}
