package com.iit.demo.web.rest.errors;

import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
//import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

/**
 * Controller advice to translate the server side exceptions to client-friendly
 * json structures.
 */
@ControllerAdvice
public class ExceptionTranslator {

    @Autowired
    MessageSource messages;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorVM processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        ErrorVM dto = new ErrorVM("VALIDATION ERROR");
        for (FieldError fieldError : fieldErrors) {
            dto.add(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
        }
        return dto;
    }

    @ExceptionHandler(MyResourceNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorVM> processRessourceNotFound(MyResourceNotFoundException exception) {
        BodyBuilder builder;
        builder = ResponseEntity.status(HttpStatus.NOT_FOUND);
        return builder.body(new ErrorVM("NOT FOUND", exception.getMessage()));
    }

    @ExceptionHandler(IllegalBusinessLogiqueException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorVM> processMethodNotSupportedException(IllegalBusinessLogiqueException exception) {
        Locale loc = LocaleContextHolder.getLocale();

        Object[] obj = new Object[1];
        if (exception.getCause() != null) {
            obj = exception.getCause().getMessage().split(";");// "14;hall;abbb"==> {14,hall,abbb}
        }
        String translatedmessage = messages.getMessage(exception.getMessage(), obj, loc);
        BodyBuilder builder;
        builder = ResponseEntity.status(HttpStatus.CONFLICT);
        return builder.body(new ErrorVM("CONFLICT", translatedmessage));
    }

}
