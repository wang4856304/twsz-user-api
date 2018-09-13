package com.twsz.dao;

import com.twsz.annotation.TableSharde;
import com.twsz.entity.bo.UserBo;
import com.twsz.tablesharde.impl.ShardeByUserCodeStrategy;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@TableSharde(tableName = "test", strategy = ShardeByUserCodeStrategy.class, shardeBy = {"userCode"})
public interface TestDao {
    Integer insert(@Param("userCode") String userCode);
    Integer insertByMap(Map<String, Object> paramMap);
    Integer insertByObj(UserBo userBo);
}
