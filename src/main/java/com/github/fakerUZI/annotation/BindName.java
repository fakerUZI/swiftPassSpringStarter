package com.github.fakerUZI.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 曹聪
 * @create 2025/3/1-14:06
 * @description 绑定字段名称
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BindName {

    String columnName() default "";

}
