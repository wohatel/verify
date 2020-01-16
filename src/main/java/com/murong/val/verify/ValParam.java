package com.murong.val.verify;


import java.lang.annotation.*;

/**
 * using on parameter on a controller.method
 * always compare whith number ,the value is limit to max
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValParam {

    boolean nullAble() default false;

    boolean blankAble() default true;

    String max() default "";

    String min() default "";

    boolean maxContains() default true;

    boolean minContains() default true;

    String strLenMin() default "";

    String strLenMax() default "";

    String msg() default "";
}
