package com.tx.core.generator.annotation;

import java.lang.annotation.*;

/**
 * 自动生成代码字段注释
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Comment {
    /**
     * 注释，说明
     * @return
     */
    String value() default "";

    /**
     * 比较长的注释说明
     * @return
     */
    String desc() default "";
}

