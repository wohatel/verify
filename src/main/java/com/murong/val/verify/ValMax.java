package com.murong.val.verify;


import java.lang.annotation.*;

/**
 * using on field of a java object which is tag by @RequestBody
 * always compare whith number ,the value is limit to max
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValMax{

    /**
     * 最大
     *
     * @return
     */
    String value();

    /**
     * 闭区间,也就是包含value 1 <= max
     */
    boolean contains() default true;

    String msg() default "";

}
