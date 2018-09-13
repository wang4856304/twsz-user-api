package com.twsz.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface TableSharde {
    /**
     * 待分表的表名
     * @return
     */
    String tableName();

    /**
     * 分表策略
     * @return
     */
    Class<?> strategy();

    /**
     * 分表条件
     * @return
     */
    String[] shardeBy();
}
