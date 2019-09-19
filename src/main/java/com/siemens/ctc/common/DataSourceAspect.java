package com.siemens.ctc.common;


import com.siemens.ctc.annotation.TargetDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Order(-1)
public class DataSourceAspect {
    private static final Logger log = LoggerFactory.getLogger(DataSourceAspect.class);

    @Pointcut("execution(* com.siemens.ctc.service.*.*(..))")
    public void pointCut() {

    }

    @Before("@annotation(targetDataSource)")
    public void setDataSource(JoinPoint point, TargetDataSource targetDataSource) {
        DatabaseType databaseType = targetDataSource.type();
        if (databaseType == DatabaseType.CTC_POSTGRES) {
            log.info(String.format("设置数据源为：%s", DatabaseType.CTC_POSTGRES));
            DatabaseContextHolder.setDatabaseType(DatabaseType.CTC_POSTGRES);
        } else {
            log.info(String.format("使用默认数据源：%s", DatabaseType.CTC_MONGODB));
            DatabaseContextHolder.setDatabaseType(DatabaseType.CTC_MONGODB);
        }
    }


    @After("@annotation(targetDataSource)")
    public void after(JoinPoint point, TargetDataSource targetDataSource) {
        log.info(String.format("当前数据源：%s, 执行清理方法。", targetDataSource.type()));
        DatabaseContextHolder.clearDatabaseType();
    }

    @Before(value = "pointCut()")
    public void doBeforWithSlave(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint.getTarget().getClass().getDeclaredMethod(joinPoint.getSignature().getName(), method.getParameterTypes());
            } catch (NoSuchMethodException e) {
                log.error("方法不存在！", e);
            }
        }
        if (null == method.getAnnotation(TargetDataSource.class)) {
            DatabaseContextHolder.setDatabaseType(DatabaseType.CTC_POSTGRES);
        }
    }
}
