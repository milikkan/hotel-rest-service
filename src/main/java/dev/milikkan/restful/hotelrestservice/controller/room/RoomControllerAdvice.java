package dev.milikkan.restful.hotelrestservice.controller.room;

import dev.milikkan.restful.hotelrestservice.exception.RoomNotFoundException;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class RoomControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<VndErrors> notFoundException(RoomNotFoundException ex) {
        return error(ex, HttpStatus.NOT_FOUND, ex.getId().toString());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<VndErrors> illegalArgumentException(IllegalArgumentException ex) {
        return error(ex, HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
    }

    private ResponseEntity<VndErrors> error(Exception ex, HttpStatus httpStatus, String log) {
        String message = Optional.of(ex.getMessage())
                .orElse(ex.getClass().getSimpleName());
        return new ResponseEntity<>(new VndErrors(log, message), httpStatus);
    }
}
