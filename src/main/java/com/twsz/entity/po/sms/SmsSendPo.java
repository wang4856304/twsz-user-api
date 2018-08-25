package com.twsz.entity.po.sms;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author tianyh 
 * @Description:普通短信发送响应实体类
 */

@Data
@NoArgsConstructor
public class SmsSendPo {
	/**
	 * 响应时间
	 */
	private String time;
	/**
	 * 消息id
	 */
	private String msgId;
	/**
	 * 状态码说明（成功返回空）
	 */
	private String errorMsg;
	/**
	 * 状态码（详细参考提交响应状态码）
	 */
	private String code;

	@Override
	public String toString() {
		return "SmsSingleResponse [time=" + time + ", msgId=" + msgId + ", errorMsg=" + errorMsg + ", code=" + code
				+ "]";
	}
	
	
	

}
