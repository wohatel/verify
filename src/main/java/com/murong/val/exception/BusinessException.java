package com.murong.val.exception;

/**
 * 业务异常expection
 *
 * @author murong
 * 2019年2月31日
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private int code;
    private String description;

    public BusinessException(int code, String description) {
        super(description);
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
