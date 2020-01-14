package com.murong.val.verify;


import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValStringLen {

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String msg() default "";

}
