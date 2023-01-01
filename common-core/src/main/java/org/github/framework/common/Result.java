package org.github.framework.common;

import org.github.framework.common.exception.ErrorCode;
import org.github.framework.common.exception.IError;

import java.io.Serializable;

public class Result<T> implements  Message<String>, Serializable {


    public  static final  String SUCCESS_CODE = "0000";

    public  static final  String SUCCESS_MSG = "success";


    protected String code;

    protected String message;

    protected boolean success;

    protected  T data;

    protected  T errorDetail;


    public static Result ok(){
        return ok(null);
    }

    public  static <T> Result ok(final T data) {
        return ok(data,SUCCESS_CODE,SUCCESS_MSG);
    }

    private static <T> Result ok(T data, String code, String msg) {
        final  Result<T> result = new Result<T>();
        result.setData(data);
        result.setSuccess(true);
        result.setCode(code);
        result.setMessage(msg);
        return  result;
    }

    public static Result fail(){
        return fail(ErrorMessage.DEFAULT_ERROR_CODE,ErrorMessage.DEFAULT_ERROR_MESSAGE,null);
    }


    public static <T> Result<T> fail(IError error){
        String code = error.code();
        String message = error.message();
        return fail(code, message);
    }


    public static Result fail(final String code, final String message) {
        return fail(code, message, null);
    }


    private static <T> Result<T> fail(String code, String msg, T detail) {
        final Result errorResult = new Result();
        errorResult.setCode(code);
        errorResult.setMessage(msg);
        errorResult.setErrorDetail(detail);
        errorResult.setSuccess(false);
        return errorResult;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(T errorDetail) {
        this.errorDetail = errorDetail;
    }

    public static void main(String[] args) {
        Result errorResult = fail("401","错误","不支持get方法请求");
        Result errorResult2 = fail(ErrorCode.AUTH);
        System.out.println(errorResult.errorDetail);
        System.out.println(errorResult2.message);
    }
}
