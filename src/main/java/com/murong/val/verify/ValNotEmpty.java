package com.murong.val.verify;

import java.lang.annotation.*;

/**
 * using on field of a java object which is tag by @RequestBody
 * the filed is type of map,list,set...
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValNotEmpty {
    String msg() default "";
}
