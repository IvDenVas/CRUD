package com.example.crud.exception;
/**
 * Класс возникновения исключения во время выполнения, расширяющий RuntimeException.
 */
public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
