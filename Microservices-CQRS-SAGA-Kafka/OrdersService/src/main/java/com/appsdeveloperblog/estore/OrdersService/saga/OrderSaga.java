package com.appsdeveloperblog.estore.OrdersService.saga;

import com.appsdeveloperblog.estore.OrdersService.core.events.OrderCreatedEvent;
import com.appsdeveloperblog.estore.core.commands.ReserveProductCommand;
import com.appsdeveloperblog.estore.core.events.ProductReservedEvent;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

//Ok

@Saga
public class OrderSaga {
	
	@Autowired
	private transient CommandGateway commandGateway;

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderSaga.class);

	@StartSaga
	@SagaEventHandler(associationProperty="orderId")
	public void handle(OrderCreatedEvent orderCreatedEvent) {
		ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
				.orderId(orderCreatedEvent.getOrderId())
				.productId(orderCreatedEvent.getProductId())
				.quantity(orderCreatedEvent.getQuantity())
				.userId(orderCreatedEvent.getUserId())
				.build();

		LOGGER.info("OrderCreatedEvent handled for orderId: " + reserveProductCommand.getOrderId() +
				" and productId: " + reserveProductCommand.getProductId() );

		commandGateway.send(reserveProductCommand, new CommandCallback<ReserveProductCommand, Object>() {

			@Override
			public void onResult(CommandMessage<? extends ReserveProductCommand> commandMessage,
								 CommandResultMessage<? extends Object> commandResultMessage) {
				if(commandResultMessage.isExceptional()) {
					// Start a compensating transaction
					/*RejectOrderCommand rejectOrderCommand = new RejectOrderCommand(orderCreatedEvent.getOrderId(),
							commandResultMessage.exceptionResult().getMessage());

					commandGateway.send(rejectOrderCommand);*/
				}

			}

		});
	}

	@SagaEventHandler(associationProperty="orderId")
	public void handle(ProductReservedEvent productReservedEvent) {
		// Process user payment
		LOGGER.info("ProductReserveddEvent is called for productId: "+ productReservedEvent.getProductId() +
				" and orderId: " + productReservedEvent.getOrderId());

		//Process user Payment

		/*FetchUserPaymentDetailsQuery fetchUserPaymentDetailsQuery =
				new FetchUserPaymentDetailsQuery(productReservedEvent.getUserId());

		User userPaymentDetails = null;

		try {
			userPaymentDetails = queryGateway.query(fetchUserPaymentDetailsQuery, ResponseTypes.instanceOf(User.class)).join();
		} catch(Exception ex) {
			LOGGER.error(ex.getMessage());

			// Start compensating transaction
			cancelProductReservation(productReservedEvent,ex.getMessage());
			return;
		}

		if(userPaymentDetails == null) {
			// Start compensating transaction
			cancelProductReservation(productReservedEvent,"Could not fetch user payment details");
			return;
		}

		LOGGER.info("Successfully fetched user payment details for user " + userPaymentDetails.getFirstName());


		scheduleId =  deadlineManager.schedule(Duration.of(120, ChronoUnit.SECONDS),
				PAYMENT_PROCESSING_TIMEOUT_DEADLINE, productReservedEvent);

		ProcessPaymentCommand proccessPaymentCommand = ProcessPaymentCommand.builder()
				.orderId(productReservedEvent.getOrderId())
				.paymentDetails(userPaymentDetails.getPaymentDetails())
				.paymentId(UUID.randomUUID().toString())
				.build();

		String result = null;
		try {
			result = commandGateway.sendAndWait(proccessPaymentCommand);
		} catch(Exception ex) {
			LOGGER.error(ex.getMessage());
			// Start compensating transaction
			cancelProductReservation(productReservedEvent,ex.getMessage());
			return;
		}

		if(result == null) {
			LOGGER.info("The ProcessPaymentCommand resulted in NULL. Initiating a compensating transaction");
			// Start compensating transaction
			cancelProductReservation(productReservedEvent, "Could not proccess user payment with provided payment details");
		}*/

	}



}
