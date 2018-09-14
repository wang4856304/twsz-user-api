package com.twsz.tablesharde.impl;

import com.twsz.tablesharde.TableStrategy;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.reflection.MetaObject;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

public class ShardeByUserCodeStrategy implements TableStrategy {
    @Override
    public String doSharde(MetaObject metaStatementHandler, String tableName, String[] shardeBy) throws Exception{
        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");//获取sql语句
        String originSql = boundSql.getSql();
        boundSql.getParameterMappings();
        String userCode = shardeBy[0];
        Object parameterObject = metaStatementHandler.getValue("delegate.boundSql.parameterObject");//获取参数
        if (parameterObject instanceof String) {
            originSql = originSql.replaceAll(tableName, tableName + "_" + parameterObject);
        }
        else if (parameterObject instanceof Map) {
            Map<String, Object> map = (Map<String, Object>)parameterObject;
            Set<String> set = map.keySet();
            String value = "";
            for (String key: set) {
                if (key.equals(userCode)) {
                    value = map.get(userCode).toString();
                    break;
                }
            }
            originSql = originSql.replaceAll(tableName, tableName + "_" + value);
        }
        else {
            Class<?> clazz = parameterObject.getClass();
            String value = "";
            Field[] fields = clazz.getDeclaredFields();
            for (Field field: fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (fieldName.equals(userCode)) {
                    value = field.get(parameterObject).toString();
                    break;
                }
            }
            originSql = originSql.replaceAll(tableName, tableName + "_" + value);
        }
        return originSql;
    }
}
