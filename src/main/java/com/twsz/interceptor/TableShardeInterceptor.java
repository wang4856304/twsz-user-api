package com.twsz.interceptor;

import com.twsz.annotation.TableSharde;
import com.twsz.exception.BusinessException;
import com.twsz.tablesharde.TableStrategy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.util.Properties;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class}) })
public class TableShardeInterceptor implements Interceptor {

    private static Log log = LogFactory.getLog(TableShardeInterceptor.class);
    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static final ReflectorFactory REFLECTOR_FACTORY = new DefaultReflectorFactory();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY, REFLECTOR_FACTORY);
        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");//获取sql语句
        String originSql = boundSql.getSql();
        log.info("boundSql:" + originSql);
        if (!StringUtils.isEmpty(originSql)) {
            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
            String id = mappedStatement.getId();
            String className = id.substring(0, id.lastIndexOf("."));
            Class<?> clazz = Class.forName(className);
            TableSharde tableSharde = clazz.getAnnotation(TableSharde.class);
            String newSql = "";
            if (tableSharde != null) {
                String tableName = tableSharde.tableName();
                String[] shardeBy = tableSharde.shardeBy();
                Class<?> strategyClazz = tableSharde.strategy();
                TableStrategy tableStrategy = (TableStrategy)strategyClazz.newInstance();
                newSql = tableStrategy.doSharde(metaStatementHandler, tableName, shardeBy);
                log.info("newSql:" + newSql);
                metaStatementHandler.setValue("delegate.boundSql.sql", newSql);
            }

        }
        else {
            log.error("TableShardeInterceptor error,sql is empty");
            throw new BusinessException("TableShardeInterceptor error,sql is empty");
        }

        //Object parameterObject = metaStatementHandler.getValue("delegate.boundSql.parameterObject");//获取参数

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
