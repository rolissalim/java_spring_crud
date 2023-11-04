package com.example.demo_crud.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

public class HandleError {
    
    @Autowired
    private Errors errors;

    public HandleError(Errors errors) {
        this.errors = errors;
    }

    public HandleError() {
    }

    private List<String> message = new ArrayList<>();

    public List<String> getError() {
        for (ObjectError error : this.errors.getAllErrors()) {
            this.message.add(error.getDefaultMessage());
        }
        return this.message;
    }

}
