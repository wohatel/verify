package com.murong.val.verify;

import java.lang.annotation.*;

/**
 * using on field of a java object which is tag by @RequestBody
 * the filed should not be null
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValNotNull {
    String msg() default "";
}
