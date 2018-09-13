package com.twsz.tablesharde;

import org.apache.ibatis.reflection.MetaObject;

public interface TableStrategy {
    String doSharde(MetaObject metaStatementHandler, String tableName, String[] shardeBy);
}
