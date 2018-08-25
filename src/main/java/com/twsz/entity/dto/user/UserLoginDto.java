package com.twsz.entity.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * }
 *
 * @author Administrator
 * @Description:
 * @date 2018/8/17 19:15
 */

@Data
@NoArgsConstructor
public class UserLoginDto {
    /**
     * 电话
     */
    @Pattern(regexp = "^[1][3,4,5,7,8][0-9]{9}$", message = "请输入正确的电话号码")
    private String mobile;

    /**
     * 用户密码
     */
    @Size(max = 16, min = 4, message = "请输入4-16个字符的密码")
    @NotBlank(message = "密码为空")
    private String password;
    /**
     * 客户端地址
     */
    private String remoteIp;

}
