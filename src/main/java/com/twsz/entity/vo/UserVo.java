package com.twsz.entity.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * }
 *
 * @author Administrator
 * @Description:
 * @date 2018/8/17 14:10
 */

@Data
@NoArgsConstructor
public class UserVo {
    private String userId;
    private String userToken;
    private String nickName;
    private String mobile;
    private String userIcon;
}
