package com.twsz.dao.impl;

import com.twsz.dao.BaseDao;
import com.twsz.dao.TestDao;
import com.twsz.entity.bo.UserBo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TestDaoImpl extends BaseDao implements TestDao {
    @Override
    public Integer insert(@Param("userCode")String userCode) {
        return masterSqlSessionTemplate.insert("com.twsz.dao.TestDao.insert", userCode);
    }

    @Override
    public Integer insertByMap(Map<String, Object> paramMap) {
        return masterSqlSessionTemplate.insert("com.twsz.dao.TestDao.insertByMap", paramMap);
    }

    @Override
    public Integer insertByObj(UserBo userBo) {
        return masterSqlSessionTemplate.insert("com.twsz.dao.TestDao.insertByObj", userBo);
    }
}
