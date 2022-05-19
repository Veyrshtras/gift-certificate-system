package com.epam.esm.exceptions;

import com.epam.esm.configs.languages.TranslatorConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import static com.epam.esm.exceptions.ErrorResponseExceptionCodes.*;
import static org.springframework.http.HttpStatus.*;

/**
 * Class {@code ExceptionsHandler} presents entity which will be returned from controller
 * in case generating exceptions
 *
 * @author Shohboz Juraev
 *
 */

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(MyDtoException.class)
    public final String handleDaoExceptions(MyDtoException ex) {
        String details = TranslatorConfig.toLocale(ex.getLocalizedMessage());
        ErrorResponses errorResponse = new ErrorResponses(NOT_FOUND, details, NOT_FOUND_EXCEPTION.getCode());
        return errorResponse.toString();
    }

    @ExceptionHandler(value = {IncorrectParamException.class})
    public final String handleIncorrectParameterExceptions(IncorrectParamException ex){
        String message = TranslatorConfig.toLocale(ex.getLocalizedMessage());
        ErrorResponses errorResponse = new ErrorResponses(BAD_REQUEST, message, BAD_REQUEST_EXCEPTION.getCode());
        return errorResponse.toString();
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, JsonProcessingException.class})
    public final String handleBadRequestExceptions() {
        String details = TranslatorConfig.toLocale("exception.badRequest");
        ErrorResponses errorResponse = new ErrorResponses(BAD_REQUEST, details, BAD_REQUEST_EXCEPTION.getCode());
        return errorResponse.toString();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public final String handleBadRequestException() {
        String details = TranslatorConfig.toLocale("exception.noHandler");
        ErrorResponses errorResponse = new ErrorResponses(NOT_FOUND, details, NOT_FOUND_EXCEPTION.getCode());
        return errorResponse.toString();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final String methodNotAllowedExceptionException() {
        String details = TranslatorConfig.toLocale("exception.notSupported");
        ErrorResponses errorResponse = new ErrorResponses(METHOD_NOT_ALLOWED, details, METHOD_NOT_ALLOWED_EXCEPTION.getCode());
        return errorResponse.toString();
    }

}
