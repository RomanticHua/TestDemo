package com.example.a10616.testdemo.custom_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
//@Retention(RetentionPolicy.CLASS)
// 子类是否可以使用父类的注解 ，因为子类默认具有父类的方法，因此此字段对方法注释无效，不管加不加，子类的方法默认使用父类的注解
// 此字段如果标识，表示子类 的类名 可以 使用父类的注解， 如果不添加此字段，那么子类的类注解不能使用父类的
// 继承只能继承类的，不能继承接口的
@Inherited
public @interface Description {

    String desc();// 成员变量以无参无异常的方法声明


    int age() default 18;  // 可以通过default来指定一个默认值.

}
