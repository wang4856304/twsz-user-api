package com.twsz.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author wangjun
 * @date 18-3-13 下午6:59
 * @description
 * @modified by
 */

@Repository
public class BaseDao {

    @Autowired
    @Qualifier("masterSqlSessionTemplate")
    protected SqlSessionTemplate masterSqlSessionTemplate;
}
