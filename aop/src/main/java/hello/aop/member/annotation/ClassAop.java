package hello.aop.member.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // 애너테이션 적용 대상을 클래스로 지정한다는 뜻
@Retention(RetentionPolicy.RUNTIME) // 애플리케이션이 실행될 때까지 이 애너테이션이 살아있다는 뜻
public @interface ClassAop {
}
