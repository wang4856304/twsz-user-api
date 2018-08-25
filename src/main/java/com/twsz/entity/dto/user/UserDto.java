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
 * @date 2018/8/16 21:47
 */
@Data
@NoArgsConstructor
public class UserDto {
    /**
     * 电话
     */
    @Pattern(regexp = "^[1][3,4,5,7,8][0-9]{9}$", message = "请输入正确的电话号码")
    private String mobile;
    /**
     * 验证码
     */
    @NotBlank(message = "验证码不可为空")
    private String verifyCode;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 昵称
     */
    @Size(max = 10, min =  4, message = "请输入4-10个字符的昵称")
    @NotBlank(message = "昵称为空")
    private String nickName;
    /**
     * 用户密码
     */
    @Size(max = 16, min = 4, message = "请输入4-16个字符的密码")
    @NotBlank(message = "密码为空")
    private String password;

    /**
     * 用户确认密码
     */
    @Size(max = 16, min = 4, message = "请输入4-16个字符的密码")
    @NotBlank(message = "确认密码为空")
    private String confirmPassword;

    /**
     * 客户端地址
     */
    private String remoteIp;
    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 用户注册渠道
     */
    private String channelId;

    /**
     * 注册类型
     */
    private int loginType;

    /**
     * 注册来源
     */
    private int loginSource;

}
