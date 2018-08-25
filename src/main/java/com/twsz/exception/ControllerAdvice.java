package com.twsz.exception;

import com.alibaba.fastjson.JSONObject;
import com.twsz.entity.Response;
import com.twsz.enums.error.ErrorEnum;
import com.twsz.enums.error.ResultEnum;
import com.twsz.service.SendNoticeService;
import com.twsz.utils.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Set;

/**
 * @author wangjun
 * @date 18-2-9 下午4:01
 * @description 统一异常处理
 * @modified by
 */

@RestControllerAdvice
public class ControllerAdvice {

    private static final String code = ResultEnum.FAIL.getCode();
    private static Log logger = LogFactory.getLog(ControllerAdvice.class);
    private static final int EX_MSG_LEN = 800;

    @Autowired
    private SendNoticeService sendNoticeService;

    @ExceptionHandler(value = Exception.class)
    public Object exceptionHandler(Exception ex) {
        Response response = new Response();
        if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException)ex;
            if (!StringUtils.isEmpty(businessException.getErrCode())) {
                response.setCode(businessException.getErrCode());
            }
            else {
                response.setCode(code);
            }
            response.setMsg(businessException.getMessage());
        }
        //get请求参数校验异常处理
        else if (ex instanceof ValidationException) {
            if (ex instanceof ConstraintViolationException) {
                Set<ConstraintViolation<?>> set =((ConstraintViolationException) ex).getConstraintViolations();
                String validateMsg = "";
                for (ConstraintViolation<?> constraintViolation: set) {
                    validateMsg = constraintViolation.getMessage();
                    break;
                }
                response.setCode(ErrorEnum.PARAM_VALID_ERROR.getCode());
                response.setMsg(validateMsg);
                response.setServerTime(DateUtil.formartDate(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));
                logger.info("response:" + JSONObject.toJSONString(response));
                return response;
            }
        }
        else {
            response.setCode(code);
            response.setMsg(ResultEnum.FAIL.getMsg());
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            ex.printStackTrace(pw);
            String exMsg = sw.toString();
            if (!StringUtils.isEmpty(exMsg)) {
                if (exMsg.length() > 400) {
                    exMsg = exMsg.substring(0, EX_MSG_LEN) + "......";
                }
            }
            sendNoticeService.sendTextNotice(exMsg);
            logger.error(ex.getMessage(), ex);
        } finally {
            pw.close();
        }
        return response;
    }
}
