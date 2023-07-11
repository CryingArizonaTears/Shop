package by.shop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@annotation(by.shop.annotation.Logging)")
    public void logging() {
    }

    @AfterReturning(value = "logging()", returning = "returningValue")
    public void loggingAfter(JoinPoint joinPoint, Object returningValue) {
        if (returningValue != null) {
            LOG.info("Class: "
                    + joinPoint.getTarget().getClass().getSimpleName() +
                    ", method: "
                    + joinPoint.getSignature().getName() +
                    ", выходящий: "
                    + returningValue);
        }
    }

    @Before("logging()")
    public void loggingBefore(JoinPoint joinPoint) {
        if (joinPoint.getArgs() != null) {
            LOG.info("Class: "
                    + joinPoint.getTarget().getClass().getSimpleName() +
                    ", method: "
                    + joinPoint.getSignature().getName() +
                    ", входящий: "
                    + Arrays.toString(joinPoint.getArgs()));
        }
    }

    @Pointcut("@annotation(by.shop.annotation.LoggingException)")
    public void loggingException() {
    }

    @AfterReturning(value = "loggingException()", returning = "returningValue")
    public void loggingExceptionAfter(Object returningValue) {
        LOG.error(returningValue.toString());
    }
}
