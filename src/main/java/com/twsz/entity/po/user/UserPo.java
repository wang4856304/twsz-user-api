package com.twsz.entity.po.user;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * }
 *
 * @author Administrator
 * @Description:
 * @date 2018/8/17 11:44
 */
@Data
@NoArgsConstructor
public class UserPo {
    private String userId;
    private String userToken;
    private String nickName;
    private String mobile;
    private String userIcon;
    private Integer isLogin;
    /**
     * 令牌是否有效
     */
    private int tokenValid;
}
