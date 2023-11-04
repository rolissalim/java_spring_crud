package com.example.demo_crud.model;

import java.util.ArrayList;
import java.util.List;

public class ResponseData<T> {
    
    private boolean status;
    private List<String> message=new ArrayList<>();
    private T data;

    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public List<String> getMessage() {
        return message;
    }
    public void setMessage(List<String> message) {
        this.message = message;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

}
