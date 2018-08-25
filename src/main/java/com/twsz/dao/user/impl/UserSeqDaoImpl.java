package com.twsz.dao.user.impl;

import com.twsz.dao.BaseDao;
import com.twsz.dao.user.UserSeqDao;
import com.twsz.entity.bo.UserSeqBo;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 * @Title: UserSeqDaoImpl
 * @ProjectName test
 * @Description:
 * @date 2018/8/1519:18
 */

@Repository
public class UserSeqDaoImpl extends BaseDao implements UserSeqDao {
    @Override
    public Long insertUserSeq() {
        UserSeqBo userSeqBo = new UserSeqBo();
        userSeqBo.setStub("a");
        masterSqlSessionTemplate.insert("UserSeq.insertUserSeq", userSeqBo);
        return userSeqBo.getUserSeq();
    }
}
