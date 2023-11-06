package com.example.demo_crud.model;

public class ResponseDataPaging<T> extends ResponseData<T> {

    private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
