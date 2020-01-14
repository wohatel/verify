package com.murong.val.verify;

import java.lang.annotation.*;

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
