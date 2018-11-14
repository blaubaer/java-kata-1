package org.echocat.kata.java.part1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.core.convert.ConversionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@ControllerAdvice
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    private static final String PATH = "/error";

    @Nonnull
    private final ErrorAttributes errorAttributes;

    @Autowired
    public ErrorController(@Nonnull ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(value = PATH, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request, HttpServletResponse response) {
        final var attributes = getErrorAttributes(request, false);
        final var object = Map.of(
            "status", response.getStatus(),
            "error", attributes.get("error"),
            "timestamp", attributes.get("timestamp")
        );
        return new ResponseEntity<>(object, HttpStatus.valueOf(response.getStatus()));
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> handleNoSuchElementException() {
        final var object = Map.<String, Object>of(
            "status", HttpStatus.NOT_FOUND.value(),
            "error", "Not Found",
            "timestamp", new Date()
        );
        return new ResponseEntity<>(object, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException() {
        final var object = Map.<String, Object>of(
            "status", HttpStatus.NOT_ACCEPTABLE.value(),
            "error", "Not Acceptable",
            "timestamp", new Date()
        );
        return new ResponseEntity<>(object, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ConversionException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(ConversionException ex) {
        if (ex.getMostSpecificCause() instanceof IllegalArgumentException) {
            return handleIllegalArgumentException();
        }
        throw ex;
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        final var requestAttributes = new ServletWebRequest(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }

}