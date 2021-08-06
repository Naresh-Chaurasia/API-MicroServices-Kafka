package com.appsdeveloperblog.estore.ProductsService.core.errorhandling;

import org.axonframework.commandhandling.CommandExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ProductsServiceErrorHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsServiceErrorHandler.class);
	
	@ExceptionHandler(value= {IllegalStateException.class})
	public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex, WebRequest request) {

		LOGGER.info("-------------------------ProductsServiceErrorHandler/handleIllegalStateException-------------------------");

		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
		
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value= {Exception.class})
	public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());

		LOGGER.info("-------------------------ProductsServiceErrorHandler/handleOtherExceptions-------------------------");
		
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value= {CommandExecutionException.class})
	public ResponseEntity<Object> handleCommandExecutionException(CommandExecutionException ex, WebRequest request) {

		LOGGER.info("-------------------------ProductsServiceErrorHandler/handleCommandExecutionException-------------------------");

		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
		
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}


}
