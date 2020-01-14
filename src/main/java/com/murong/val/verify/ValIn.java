package com.murong.val.verify;


import java.lang.annotation.*;

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
