package br.com.taian.order.exception;

import lombok.Data;

@Data
public class InvalidAccessException extends RuntimeException {

    public InvalidAccessException(String message){
        super(message);
    }

}
