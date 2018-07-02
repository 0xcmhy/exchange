package com.ibm.exchange.Enum;

import org.apache.ibatis.jdbc.Null;

public enum  ControllerEnum {

    SUCCESS("0","成功"),
    FAIL("1","失败");

    private ControllerEnum(String code, String message){
        this.code = code;
        this.message = message;
    }

    public  ControllerEnum getState(String code){
        for(ControllerEnum controllerEnum : values()){
            if (code.equals(controllerEnum.getCode())){
                return  controllerEnum;
            }
        }
        return null;
    }
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
