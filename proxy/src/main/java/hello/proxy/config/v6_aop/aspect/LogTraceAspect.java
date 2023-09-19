package hello.proxy.config.v6_aop.aspect;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    @Around("execution(* hello.proxy.app..*(..))") // 포인트컷이라고 생각
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        // Advice 로직이 들어가는 곳

        TraceStatus status = null;

//        log.info("target={}", joinPoint.getTarget()); // 실제 호출 대상
//        log.info("getArgs={}", joinPoint.getArgs()); // 전달 인자
//        log.info("getSignature={}", joinPoint.getSignature()); // join point 시그니처

        try {
/*
            Method method = invocation.getMethod();
            String message = method.getDeclaringClass().getSimpleName() + "." +
                    method.getName() + "()";
*/
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            // 로직 호출
//            Object result = invocation.proceed();
            Object result = joinPoint.proceed();

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
