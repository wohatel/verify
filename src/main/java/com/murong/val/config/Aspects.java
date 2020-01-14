package com.murong.val.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.murong.val.exception.BusinessException;
import com.murong.val.util.StringUtils;
import com.murong.val.verify.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

@Aspect
@Configuration
public class Aspects {

    private static final int errorCode = 0;

    @Pointcut("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public void controller() {
    }

    @Before("controller()")
    public void valNewParam(JoinPoint joinPoint) throws InvocationTargetException, IllegalAccessException {
        final Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();
        method_param:
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Object arg = args[i];
            ValParam valparam = parameter.getAnnotation(ValParam.class);
            if (valparam != null) {
                verfyParam(valparam, arg, parameter);
            }
            // 如果该controller 方法上的参数有包含 @RequestBody,那么就做参数校验
            boolean valp = parameter.isAnnotationPresent(RequestBody.class);
            if (valp) {
                verfyBody(arg, parameter);
            }
        }
    }

    /**
     * 校验body体
     *
     * @param arg
     * @param parameter
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void verfyBody(Object arg, Parameter parameter) throws InvocationTargetException, IllegalAccessException {
        final Class<?> clazz = parameter.getType();
        final Field[] fields = clazz.getDeclaredFields();
        final Map map = (JSONObject) JSON.toJSON(arg);
        // 接受实体,所有标注有@ValNullable 注解的字段做校验
        class_field:
        for (int j = 0; j < fields.length; j++) {
            final Field field = fields[j];
            final Object value = map.get(field.getName());
            // 非空
            boolean valNull = field.isAnnotationPresent(ValNoneNull.class);
            if (valNull) {
                if (value == null) {
                    ValNoneNull valParam = field
                            .getAnnotationsByType(ValNoneNull.class)[0];
                    String msg = valParam.msg();
                    if (StringUtils.isBlank(valParam.msg())) {
                        msg = field.getName() + ":" + "is null";
                    }
                    throw new BusinessException(errorCode, msg);
                }
            }
            // 正则
            boolean valExp = field.isAnnotationPresent(ValExpression.class);
            if (valExp) {
                if (value != null) {
                    ValExpression valParam = field
                            .getAnnotationsByType(ValExpression.class)[0];
                    final boolean matches = value.toString()
                            .matches(valParam.value());
                    if (!matches) {
                        String msg = valParam.msg();
                        if (StringUtils.isBlank(valParam.msg())) {
                            msg = field.getName() + ":" + "格式不正确";
                        }
                        throw new BusinessException(errorCode, msg);
                    }
                }
            }

            // 最小
            boolean valMin = field.isAnnotationPresent(ValMin.class);
            if (valMin) {
                if (value != null) {
                    ValMin valParam = field
                            .getAnnotationsByType(ValMin.class)[0];
                    String fieldValue = valParam.value();
                    BigDecimal min = new BigDecimal(fieldValue);
                    BigDecimal decimal = new BigDecimal(value.toString());
                    int i1 = decimal.compareTo(min);
                    String msg = valParam.msg();
                    if (valParam.contains && i1 < 0) {
                        if (StringUtils.isBlank(valParam.msg())) {
                            msg = field.getName() + ":" + "不能小于 "
                                    + fieldValue;
                        }
                        throw new BusinessException(errorCode, msg);
                    }
                    if (!valParam.contains && i1 <= 0) {
                        if (StringUtils.isBlank(valParam.msg())) {
                            msg = field.getName() + ":" + "不能小于或等于 "
                                    + fieldValue;
                        }
                        throw new BusinessException(errorCode, msg);
                    }
                }
            }

            // 最大
            boolean valMax = field.isAnnotationPresent(ValMax.class);
            if (valMax) {
                if (value != null) {
                    ValMax valParam = field
                            .getAnnotationsByType(ValMax.class)[0];
                    String fieldValue = valParam.value();
                    BigDecimal max = new BigDecimal(fieldValue);
                    BigDecimal decimal = new BigDecimal(value.toString());
                    int i1 = decimal.compareTo(max);
                    String msg = valParam.msg();
                    if (valParam.contains && i1 > 0) {
                        if (StringUtils.isBlank(valParam.msg())) {
                            msg = field.getName() + ":" + "不能大于 "
                                    + fieldValue;
                        }
                        throw new BusinessException(errorCode, msg);
                    }
                    if (!valParam.contains && i1 >= 0) {
                        if (StringUtils.isBlank(valParam.msg())) {
                            msg = field.getName() + ":" + "不能大于或等于 "
                                    + fieldValue;
                        }
                        throw new BusinessException(errorCode, msg);
                    }
                }
            }

            // in
            boolean valIn = field.isAnnotationPresent(ValIn.class);
            if (valIn) {
                if (value != null) {
                    ValIn valParam = field
                            .getAnnotationsByType(ValIn.class)[0];
                    final boolean contains = Arrays.asList(valParam.value())
                            .contains(value.toString());
                    if (!contains) {
                        String msg = valParam.msg();
                        if (StringUtils.isBlank(valParam.msg())) {
                            msg = field.getName() + ":" + "取值范围错误";
                        }
                        throw new BusinessException(errorCode, msg);
                    }
                }
            }

            // valStringLen
            boolean valStringLen = field
                    .isAnnotationPresent(ValStringLen.class);
            if (valStringLen) {
                if (value != null) {
                    ValStringLen valParam = field
                            .getAnnotationsByType(ValStringLen.class)[0];
                    int min = valParam.min();
                    int max = valParam.max();
                    int length = value.toString().length();
                    if (length < min || length > max) {
                        String msg = valParam.msg();
                        if (StringUtils.isBlank(valParam.msg())) {
                            msg = field.getName() + ":" + "长度范围错误";
                        }
                        throw new BusinessException(errorCode, msg);
                    }
                }
            }

        }
        final Method[] methods = clazz.getDeclaredMethods();
        for (int j = 0; j < methods.length; j++) {
            Method emethod = methods[j];
            ValMethod annotation = emethod.getAnnotation(ValMethod.class);
            if (annotation != null) {
                emethod.invoke(arg);
            }
        }
    }

    /**
     * 校验参数
     *
     * @param valparam
     * @param arg
     * @param parameter
     */
    public void verfyParam(ValParam valparam, Object arg, Parameter parameter) {
        String name = parameter.getName();
        boolean isNullVap = valparam.nullAble();
        if (!isNullVap && arg == null) {
            throw new BusinessException(errorCode, name + ": can not be null");
        }
        if (arg == null) {
            return;
        }
        // 最小
        if (!StringUtils.isBlank(valparam.min())) {
            BigDecimal min = new BigDecimal(valparam.min());
            BigDecimal decimal = new BigDecimal(arg.toString());
            int i1 = decimal.compareTo(min);
            String msg = valparam.msg();
            if (valparam.minContains() && i1 < 0) {
                if (StringUtils.isBlank(msg)) {
                    msg = name + ":" + "不能小于 " + valparam.min();
                }
                throw new BusinessException(errorCode, msg);
            }
            if (!valparam.minContains() && i1 <= 0) {
                if (StringUtils.isBlank(msg)) {
                    msg = name + ":" + "不能小于或等于 " + valparam.min();
                }
                throw new BusinessException(errorCode, msg);
            }
        }
        // 最大
        if (!StringUtils.isBlank(valparam.max())) {
            BigDecimal max = new BigDecimal(valparam.max());
            BigDecimal decimal = new BigDecimal(arg.toString());
            int i1 = decimal.compareTo(max);
            String msg = valparam.msg();
            if (valparam.maxContains() && i1 > 0) {
                if (StringUtils.isBlank(msg)) {
                    msg = name + ":" + "不能大于 " + valparam.max();
                }
                throw new BusinessException(errorCode, msg);
            }
            if (!valparam.maxContains() && i1 >= 0) {
                if (StringUtils.isBlank(msg)) {
                    msg = name + ":" + "不能大于或等于 " + valparam.max();
                }
                throw new BusinessException(errorCode, msg);
            }
        }
        // stringLenMin
        if (!StringUtils.isBlank(valparam.strLenMin())) {
            int min = Integer.parseInt(valparam.strLenMin());
            if (arg.toString().length() < min) {
                String msg = valparam.msg();
                if (StringUtils.isBlank(valparam.msg())) {
                    msg = name + ":" + "长度不能小于 " + min;
                }
                throw new BusinessException(errorCode, msg);
            }
        }
        // stringLenMax
        if (!StringUtils.isBlank(valparam.strLenMax())) {
            int max = Integer.parseInt(valparam.strLenMax());
            if (arg.toString().length() > max) {
                String msg = valparam.msg();
                if (StringUtils.isBlank(valparam.msg())) {
                    msg = name + ":" + "长度不能大于 " + max;
                }
                throw new BusinessException(errorCode, msg);
            }
        }
    }
}
