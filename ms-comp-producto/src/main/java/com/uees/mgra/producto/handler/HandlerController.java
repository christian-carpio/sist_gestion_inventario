package com.uees.mgra.producto.handler;


import org.hibernate.exception.DataException;
import org.hibernate.id.IdentifierGenerationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.NoResultException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.*;


@RestControllerAdvice
public class HandlerController {
    private final static Logger log = LoggerFactory.getLogger(HandlerController.class);

    @ExceptionHandler({JpaSystemException.class})
    public ResponseEntity<ErrorDetails> handleJpaSystemException(JpaSystemException e) {
        if (e.getCause() instanceof IdentifierGenerationException) {
            return error(NOT_FOUND, null, "Error al obtener la secuencia");
        }
        return error(NOT_FOUND, e, e.getMessage());
    }


    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorDetails> handleNotFoundException(NotFoundException e) {
        return error(NOT_FOUND, e, e.getMessage());
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ErrorDetails> handleBadRequestException(BadRequestException e) {
        return error(BAD_REQUEST, e, e.getMessage());
    }

    @ExceptionHandler({DataException.class})
    public ResponseEntity<ErrorDetails> handleDataException(DataException e) {
        return error(BAD_REQUEST, e, e.getMessage());
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ErrorDetails> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return error(BAD_REQUEST, e, "Error, problemas de integridad de datos. " + e.getMessage());
    }


    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorDetails> handleDataIntegrityViolationException(AccessDeniedException e) {
        return error(FORBIDDEN, e, "Acceso generado ");
    }

    @ExceptionHandler({NoResultException.class})
    public ResponseEntity<ErrorDetails> handleNoResultException(NoResultException e) {
        return error(BAD_REQUEST, e, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> validationException(MethodArgumentNotValidException e) {
        List<String> messages = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((err) -> messages.add(err.getDefaultMessage()));

        ErrorDetails errorDetails = new ErrorDetails(new Date(),"Solicitud Incorrecta" ,messages.toString().replaceAll("[\\[\\]]", ""));
        return new ResponseEntity<>(errorDetails, BAD_REQUEST);
    }

    private ResponseEntity<ErrorDetails> error(HttpStatus status, Exception e, String mensaje) {
        log.error("Exception completa: {}", e);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), mensaje,
                e != null ? e.getMessage() : "");

        return new ResponseEntity<>(errorDetails, status);
    }
}
