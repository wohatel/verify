package com.murong.val.verify;

import java.lang.annotation.*;


/**
 * using on field of a java object which is tag by @RequestBody
 * the field's toString() should match this regx
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValExpression {

    /**
     * 正则表达式
     *
     * @return
     */
    String value();

    String msg() default "";
}
