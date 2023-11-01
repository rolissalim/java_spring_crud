package com.example.demo_crud.model;

public class ResponseDataPaging<T> extends ResponseData<T> {

    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
