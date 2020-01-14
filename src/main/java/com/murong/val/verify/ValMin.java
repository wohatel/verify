package com.murong.val.verify;


import java.lang.annotation.*;

/**
 * using on field of a java object which is tag by @RequestBody
 * always compare whith number ,the value is limit to min
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValMin {

    /**
     * 最小
     *
     * @return
     */
    String value();

    /**
     * 闭区间,包含value  1 >= min
     */
    boolean contains = true;

    String msg() default "";
}
