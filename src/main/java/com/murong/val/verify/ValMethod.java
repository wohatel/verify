package com.murong.val.verify;

import java.lang.annotation.*;

/**
 * using on method of a java object which is tag by @RequestBody
 * whith this annotation ,the method will be called
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValMethod {
    String msg() default "";
}
