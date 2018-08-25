package com.twsz.dao.account;

import com.twsz.entity.bo.AccountBo;
import com.twsz.entity.po.account.AccountPo;

/**
 * }
 *
 * @author Administrator
 * @Description:
 * @date 2018/8/19 11:35
 */
public interface AccountDao {
    Long insertAccountSeq();
    Integer insertAccount(AccountBo accountBo);
    AccountPo selectAccountByUserIdAndType(AccountBo accountBo);
    Integer updateAccountAmount(AccountBo accountBo);
}
