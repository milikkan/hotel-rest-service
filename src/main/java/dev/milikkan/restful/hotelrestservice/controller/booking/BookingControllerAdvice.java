package dev.milikkan.restful.hotelrestservice.controller.booking;

import dev.milikkan.restful.hotelrestservice.exception.BookingNotFoundException;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@ControllerAdvice(assignableTypes = BookingController.class)
@RequestMapping(produces = "application/vnd.error+json")
public class BookingControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<VndErrors> notFoundException(BookingNotFoundException ex) {
        return error(ex, HttpStatus.NOT_FOUND, ex.getId().toString());
    }

    private ResponseEntity<VndErrors> error(Exception ex, HttpStatus httpStatus, String log) {
        String message = Optional.of(ex.getMessage())
                .orElse(ex.getClass().getSimpleName());
        return new ResponseEntity<>(new VndErrors(log, message), httpStatus);
    }
}
