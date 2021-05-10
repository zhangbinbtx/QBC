package com.quaero.qbcTest.dto.MessageCode;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.quaero.qbcTest.dto.MessageCode.inter.Code;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 响应数据结构
 *
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseVO implements Serializable {

	private static final long serialVersionUID = -4513008280553125626L;

	private static final CodeEnum SUCCESS = CodeEnum.SUCCESS;

    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应数据
     */
    private Object data;
    /**
     * 响应描述
     */
    private String desc;

    /**
     * 成功响应且带响应数据
     *
     * @param data 响应数据
     */
    public ResponseVO(Object data) {
        this.code = SUCCESS.getCode();
        this.desc = SUCCESS.getDesc();
        this.data = data;
    }

    /**
     * 只带响应code和desc
     *
     * @param code 响应code
     */
    public ResponseVO(Code code,String desc) {
        this.code = code.getCode();
        this.desc = code.getDesc();
    }
    public ResponseVO(Code code,Object data) {
        this.code = code.getCode();
        this.data = data;
    }


}
