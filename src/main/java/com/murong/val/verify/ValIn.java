package com.murong.val.verify;


import java.lang.annotation.*;

/**
 * using on field of a java object which is tag by @RequestBody
 * the field's toString() should in ValIn.value
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValIn {

    /**
     * 最小
     *
     * @return
     */
    String[] value();


    String msg() default "";
}
