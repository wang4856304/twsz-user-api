package com.twsz.dao.user.impl;

import com.twsz.dao.BaseDao;
import com.twsz.dao.user.UserDao;
import com.twsz.entity.bo.UserBo;
import com.twsz.entity.po.user.UserInfoPo;
import com.twsz.entity.po.user.UserPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * }
 *
 * @author Administrator
 * @Description:
 * @date 2018/8/17 13:53
 */

@Repository
public class UserDaoImpl extends BaseDao implements UserDao {
    @Override
    public Integer insert(UserBo userBo) {
        return masterSqlSessionTemplate.insert("User.insert", userBo);
    }

    @Override
    public Integer delete(UserBo userBo) {
        return null;
    }

    @Override
    public UserPo selectByMobile(UserBo userBo) {
        return masterSqlSessionTemplate.selectOne("User.selectByMobile", userBo);
    }

    @Override
    public UserPo selectByMobileAndPwd(UserBo userBo) {
        return masterSqlSessionTemplate.selectOne("User.selectByMobileAndPwd", userBo);
    }

    @Override
    public Integer updateLastIpAndTime(UserBo userBo) {
        return masterSqlSessionTemplate.update("User.updateLastIpAndTime", userBo);
    }

    @Override
    public String selectUserIdByToken(UserBo userBo) {
        return masterSqlSessionTemplate.selectOne("User.selectUserIdByToken", userBo);
    }

    @Override
    public Integer updateLoginByToken(UserBo userBo) {
        return masterSqlSessionTemplate.update("User.updateLoginByToken", userBo);
    }

    @Override
    public UserInfoPo selectUserInfoByToken(UserBo userBo) {
        return masterSqlSessionTemplate.selectOne("User.selectUserInfoByToken", userBo);
    }

    @Override
    public Integer updatePwd(UserBo userBo) {
        return masterSqlSessionTemplate.update("User.updatePwd", userBo);
    }

    @Override
    public Integer updateResetPwd(UserBo userBo) {
        return masterSqlSessionTemplate.update("User.updateResetPwd", userBo);
    }

    @Override
    public UserPo selectByNickName(UserBo userBo) {
        return masterSqlSessionTemplate.selectOne("User.selectByNickName", userBo);
    }

    @Override
    public String selectMobileByToken(@Param("token") String token) {
        return masterSqlSessionTemplate.selectOne("User.selectMobileByToken", token);
    }
}
