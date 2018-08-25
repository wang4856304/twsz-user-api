package com.twsz.entity.po.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoPo extends UserPo {
    private Long tokenExpire;
    private String email;
    private Integer pushMsg;
    private Integer isRecharge;
    private Integer status;
    private Integer display;
    private Integer userLevel;
    private Integer channelId;
}
