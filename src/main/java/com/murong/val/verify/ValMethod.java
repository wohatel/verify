package com.murong.val.verify;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValMethod {
    String msg() default "";
}
