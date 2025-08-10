package com.dev.apirestaurantrank.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    String message;
    String error;
    int code;
}
