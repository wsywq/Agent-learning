package com.agent.learning.exception;

/**
 * 业务异常类
 * 
 * @author Agent Learning Team
 * @version 1.0.0
 * @since 2024-12-19
 */
public class BusinessException extends RuntimeException {

    /**
     * 错误代码
     */
    private String code;

    /**
     * 构造函数
     * 
     * @param message 错误消息
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * 构造函数
     * 
     * @param code    错误代码
     * @param message 错误消息
     */
    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 构造函数
     * 
     * @param message 错误消息
     * @param cause   原因
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 获取错误代码
     * 
     * @return 错误代码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置错误代码
     * 
     * @param code 错误代码
     */
    public void setCode(String code) {
        this.code = code;
    }
}
