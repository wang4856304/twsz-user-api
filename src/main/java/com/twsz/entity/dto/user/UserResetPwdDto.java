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
 * @date 2018/8/20 16:46
 */

@Data
@NoArgsConstructor
public class UserResetPwdDto {

    /**
     * 用户令牌
     */
    @NotBlank(message = "用户令牌为空")
    private String token;

    /**
     * 电话
     */
    @Pattern(regexp = "^[1][3,4,5,7,8][0-9]{9}$", message = "请输入正确的电话号码")
    private String mobile;

    /**
     * 用户新密码
     */
    @Size(max = 16, min = 4, message = "请输入4-16个字符的密码")
    @NotBlank(message = "新密码为空")
    private String password;

    /**
     * 用户确认密码
     */
    @Size(max = 16, min = 4, message = "请输入4-16个字符的密码")
    @NotBlank(message = "确认密码为空")
    private String confirmPassword;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不可为空")
    private String verifyCode;
}
