package com.murong.val.verify;


import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValMax {

    /**
     * 最大
     *
     * @return
     */
    String value();

    /**
     * 闭区间,也就是包含value 1 <= max
     */
    boolean contains = true;

    String msg() default "";

}
