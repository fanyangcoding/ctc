package com.siemens.ctc.annotation;



import com.siemens.ctc.common.DatabaseType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetDataSource {
    DatabaseType type() default DatabaseType.CTC_POSTGRES;
}
