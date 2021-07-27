package com.study.security.auth.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) //이 어노테이션이 생성될수 있는 위치, 메소드의 파라미터로 선언된 객체에서만 사용가능
@Retention(RetentionPolicy.RUNTIME)//컴파일이후에도 jvm에 의해 참조 가능
public @interface LoginUser {

}
