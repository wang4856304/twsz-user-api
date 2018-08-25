package com.twsz.service;

import com.twsz.entity.Response;
import com.twsz.enums.error.ResultEnum;

/**
 * }
 *
 * @author Administrator
 * @Description:
 * @date 2018/8/17 19:43
 */
public abstract class BaseService {
    public Response buildSuccesResponse() {
        Response response = new Response();
        response.setCode(ResultEnum.SUCCESS.getCode());
        response.setMsg(ResultEnum.SUCCESS.getMsg());
        return response;
    }

    public Response buildSuccesResponse(Object data) {
        Response response = new Response();
        response.setCode(ResultEnum.SUCCESS.getCode());
        response.setMsg(ResultEnum.SUCCESS.getMsg());
        response.setData(data);
        return response;
    }
    public Response buildErrorResponse(String code, String msg) {
        Response response = new Response();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

}
