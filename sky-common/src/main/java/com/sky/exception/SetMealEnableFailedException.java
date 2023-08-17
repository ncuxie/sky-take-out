package com.sky.exception;

/**
 * 套餐启用失败异常
 */
public class SetMealEnableFailedException extends BaseException {

    public SetMealEnableFailedException(){}

    public SetMealEnableFailedException(String msg){
        super(msg);
    }
}
