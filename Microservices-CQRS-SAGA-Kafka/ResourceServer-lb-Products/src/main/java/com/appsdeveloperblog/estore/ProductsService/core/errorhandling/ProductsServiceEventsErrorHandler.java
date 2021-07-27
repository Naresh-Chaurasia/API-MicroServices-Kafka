package com.appsdeveloperblog.estore.ProductsService.core.errorhandling;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// This is called, when error is thrown by event handler
public class ProductsServiceEventsErrorHandler implements ListenerInvocationErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListenerInvocationErrorHandler.class);

	@Override
	public void onError(Exception exception, EventMessage<?> event, EventMessageHandler eventHandler) throws Exception {

        LOGGER.info("-------------------------This is called, when error is thrown by event handler-------------------------");
	    LOGGER.info("-------------------------ProductsServiceEventsErrorHandler/onError()-------------------------");

	    throw exception;

	}

}