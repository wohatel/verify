package com.murong.val.verify;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValNoneNull {
    String msg() default "";
}
