package com.gslab.parivahan.security;

import com.fasterxml.jackson.annotation.JsonValue;
/*
 * 
 * @author Swapnil Kashid
 * 
 * */
public enum ErrorCode {
	GLOBAL(2),

    AUTHENTICATION(10), JWT_TOKEN_EXPIRED(11);
    
    private int errorCode;

    private ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}
