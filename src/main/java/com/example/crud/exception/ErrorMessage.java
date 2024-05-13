package com.example.crud.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
/**
 * Класс сообщения об ошибке.
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorMessage {
    private String message;
}
