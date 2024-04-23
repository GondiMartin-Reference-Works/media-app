package com.example.mediaApp.exception;

public class UserAlreadyExistsExeption extends Exception{

    private final String email;

    public UserAlreadyExistsExeption(String message, String email){
        super(message);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
