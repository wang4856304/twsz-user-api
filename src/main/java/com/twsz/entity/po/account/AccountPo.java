package com.twsz.entity.po.account;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * }
 *
 * @author Administrator
 * @Description:
 * @date 2018/8/19 12:10
 */

@Data
@NoArgsConstructor
public class AccountPo {
    private String userAccountId;
    private String userId;
    private String userAccountType;
    private String userAccountName;
    private double amount;
    private int status;
    private String createTime;
}
