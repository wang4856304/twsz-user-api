package com.twsz.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangjun
 * @date 18-2-9 下午6:09
 * @description
 * @modified by
 */

@Data
@NoArgsConstructor
public class Response {

    private String code;
    private String msg;
    private Object data;
    private String serverTime;
}
