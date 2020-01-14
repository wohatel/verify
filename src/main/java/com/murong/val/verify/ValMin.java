package com.murong.val.verify;


import java.lang.annotation.*;

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
