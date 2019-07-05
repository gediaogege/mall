package com.example.mall.comment;

/**
 * 封装返回结果
 */
public class CommentResult {
    private int code;
    private String message;
    private Object data;

    public CommentResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 返回成功
     *
     * @param
     * @return
     */
    public static CommentResult success() {
        return success(ResultCode.SUCCESS);
    }

    public static CommentResult success(Object data) {
        return new CommentResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static CommentResult success(Object data, String message) {
        return new CommentResult(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 返回失败
     *
     * @param data
     * @return
     */
    public static CommentResult failed(Object data) {
        return new CommentResult(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), data);
    }

    public static CommentResult failed(Object data, String message) {
        return new CommentResult(ResultCode.FAILED.getCode(), message, data);
    }

    /**
     * 失败返回结果
     */
    public static CommentResult failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static CommentResult validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     */
    public static CommentResult validateFailed(String message) {
        return new CommentResult(ResultCode.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static CommentResult unauthorized(Object data) {
        return new CommentResult(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    /**
     * 未授权返回结果
     */
    public static CommentResult forbidden(Object data) {
        return new CommentResult(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }
}
