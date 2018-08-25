package com.twsz.dao.account.impl;

import com.twsz.dao.BaseDao;
import com.twsz.dao.account.AccountDao;
import com.twsz.entity.bo.AccountBo;
import com.twsz.entity.bo.AccountSeqBo;
import com.twsz.entity.po.account.AccountPo;
import org.springframework.stereotype.Repository;

/**
 * }
 *
 * @author Administrator
 * @Description:
 * @date 2018/8/19 11:36
 */
@Repository
public class AccountDaoImpl extends BaseDao implements AccountDao {
    @Override
    public Long insertAccountSeq() {
        AccountSeqBo accountSeqBo = new AccountSeqBo();
        accountSeqBo.setStub("a");
        masterSqlSessionTemplate.insert("Account.insertAccountSeq", accountSeqBo);
        return accountSeqBo.getAccountSeq();
    }

    @Override
    public Integer insertAccount(AccountBo accountBo) {
        return masterSqlSessionTemplate.insert("Account.insertAccount", accountBo);
    }

    @Override
    public AccountPo selectAccountByUserIdAndType(AccountBo accountBo) {
        return masterSqlSessionTemplate.selectOne("Account.selectAccountByUserIdAndType", accountBo);
    }

    @Override
    public Integer updateAccountAmount(AccountBo accountBo) {
        return masterSqlSessionTemplate.update("Account.updateAccountAmount", accountBo);
    }
}
