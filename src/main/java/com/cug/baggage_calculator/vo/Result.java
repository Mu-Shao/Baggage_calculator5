package com.cug.baggage_calculator.vo;

import lombok.Data;

@Data
public class Result {
    private boolean success;
    private int code;
    private String message;
    private Double price;

    //    返回成功
    public static Result success(String msg, Double price){
        return new Result(true,200,msg,price);
    }
    //    返回失败
    public static Result fail(int code,String msg){
        return new Result(false,code,msg,null);
    }

    public Result(boolean success, int code, String message, Double price) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.price = price;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
