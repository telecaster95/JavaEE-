package com.iaven.utils;

public class ReturnObject<T> {
    public T getObject() {
        return obj;
    }

    public void setObject(T object) {
        this.obj = object;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String code;
    private T obj;
    public ReturnObject(String code, T object)
    {
        this.obj = object;
        this.code = code;
    }

}
