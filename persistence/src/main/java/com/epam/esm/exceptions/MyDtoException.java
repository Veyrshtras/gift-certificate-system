package com.epam.esm.exceptions;

public class MyDtoException extends Exception{

    public MyDtoException() {}
    public MyDtoException(String messageCode) {
        super(messageCode);
    }
    public MyDtoException(String messageCode, Throwable cause) {
        super(messageCode, cause);
    }
    public MyDtoException(Throwable cause) {
        super(cause);
    }
}
