package com.twsz.entity.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * }
 *
 * @author Administrator
 * @Description:
 * @date 2018/8/19 11:45
 */

@Data
@NoArgsConstructor
public class AccountBo {
    private String userAccountId;
    private String userId;
    private int userAccountType;
    private String userAccountName;
    private double amount;
    private int status;
}
