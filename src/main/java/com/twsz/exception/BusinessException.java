package com.twsz.exception;

import lombok.Data;

/**
 * @author wangjun
 * @date 18-2-9 下午4:04
 * @description
 * @modified by
 */

@Data
public class BusinessException extends RuntimeException {

    private String errCode;

    private String errMsg;

    public BusinessException(String errMsg) {
        super(errMsg);
        this.errMsg = errMsg;
    }

    public BusinessException(String errCode ,String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public BusinessException(String errMsg, Throwable e) {
        super(errMsg, e);
        this.errMsg = errMsg;
    }

    public BusinessException(String errCode, String errMsg, Throwable e) {
        super(errMsg, e);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
}
