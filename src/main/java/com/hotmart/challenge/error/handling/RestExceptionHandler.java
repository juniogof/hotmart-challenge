/**
 * 
 */
package com.hotmart.challenge.error.handling;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * Class for manage exceptions
 * @author junio
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
		log.info("Handling EntityNotFoundException.java");
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
		log.info("Handling MethodArgumentNotValidException.java");
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Argument(s) is wrong");
		
		List<ApiSubError> subErrors = new ArrayList<>();
		
	    ex.getBindingResult().getAllErrors().forEach(error -> {
	    	FieldError fieldError = (FieldError) error;
	        ApiValidationError apiValidationError = new ApiValidationError();
	        apiValidationError.setField(fieldError.getField());
	        apiValidationError.setMessage(fieldError.getDefaultMessage());
	        apiValidationError.setRejectedValue(fieldError.getRejectedValue());
	        subErrors.add(apiValidationError);
	    });
	    
	    apiError.setSubErrors(subErrors);
	    
		return buildResponseEntity(apiError);
	}
	
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}