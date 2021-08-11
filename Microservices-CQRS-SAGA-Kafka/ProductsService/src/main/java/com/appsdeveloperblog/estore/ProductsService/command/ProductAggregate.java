package com.appsdeveloperblog.estore.ProductsService.command;


import com.appsdeveloperblog.estore.ProductsService.core.events.ProductCreatedEvent;
import com.appsdeveloperblog.estore.core.commands.CancelProductReservationCommand;
import com.appsdeveloperblog.estore.core.commands.ReserveProductCommand;
import com.appsdeveloperblog.estore.core.events.ProductReservationCancelledEvent;
import com.appsdeveloperblog.estore.core.events.ProductReservedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

//@Aggregate(snapshotTriggerDefinition="productSnapshotTriggerDefinition")
@Aggregate
public class ProductAggregate {
	
	@AggregateIdentifier
	private String productId;
	private String title;
	private BigDecimal price;
	private Integer quantity;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductAggregate.class);
	
	public ProductAggregate() {
		
	}
	
	@CommandHandler
	public ProductAggregate(CreateProductCommand createProductCommand) throws Exception {
		// Validate Create Product Command

		LOGGER.info("-------------------------ProductAggregate/ProductAggregate(CreateProductCommand createProductCommand)/@CommandHandler-------------------------");
		
		if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Price cannot be less or equal than zero");
		}
		
		if(createProductCommand.getTitle() == null 
				|| createProductCommand.getTitle().isBlank()) {
			throw new IllegalArgumentException("Title cannot be empty");
		}
		
		ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
		
		BeanUtils.copyProperties(createProductCommand, productCreatedEvent);

		//Publishing Events
		LOGGER.info("-------------------------ProductAggregate/ProductAggregate/Publishing Events-------------------------");
		LOGGER.info("-------------------------It will dispatch all the event to all event handlers inside this aggregate-------------------------");
		AggregateLifecycle.apply(productCreatedEvent);



	}
	
	@CommandHandler
	public void handle(ReserveProductCommand reserveProductCommand) {
		
		if(quantity < reserveProductCommand.getQuantity()) {
			throw new IllegalArgumentException("Insufficient number of items in stock");
		}
		
		ProductReservedEvent productReservedEvent = ProductReservedEvent.builder()
				.orderId(reserveProductCommand.getOrderId())
				.productId(reserveProductCommand.getProductId())
				.quantity(reserveProductCommand.getQuantity())
				.userId(reserveProductCommand.getUserId())
				.build();
		
		AggregateLifecycle.apply(productReservedEvent);
		
	}
	
	@CommandHandler
	public void handle(CancelProductReservationCommand cancelProductReservationCommand) {

		LOGGER.info("-------------------------ProductAggregate/handle(CancelProductReservationCommand cancelProductReservationCommand)/@CommandHandler-------------------------");
		
		ProductReservationCancelledEvent productReservationCancelledEvent =
				ProductReservationCancelledEvent.builder()
				.orderId(cancelProductReservationCommand.getOrderId())
				.productId(cancelProductReservationCommand.getProductId())
				.quantity(cancelProductReservationCommand.getQuantity())
				.reason(cancelProductReservationCommand.getReason())
				.userId(cancelProductReservationCommand.getUserId())
				.build();

		LOGGER.info("-------------------------call :: AggregateLifecycle.apply(productReservationCancelledEvent)-------------------------");
		AggregateLifecycle.apply(productReservationCancelledEvent);
		
	}
	
	
	@EventSourcingHandler
	public void on(ProductReservationCancelledEvent productReservationCancelledEvent) {

		LOGGER.info("-------------------------ProductAggregate/on(ProductReservationCancelledEvent productReservationCancelledEvent)/@EventSourcingHandler-------------------------");
		this.quantity += productReservationCancelledEvent.getQuantity();
	}
	
	
	@EventSourcingHandler
	public void on(ProductCreatedEvent productCreatedEvent) {
		LOGGER.info("-------------------------ProductAggregate/on(ProductCreatedEvent productCreatedEvent)/@EventSourcingHandler-------------------------");
		this.productId = productCreatedEvent.getProductId();
		this.price = productCreatedEvent.getPrice();
		this.title = productCreatedEvent.getTitle();
		this.quantity = productCreatedEvent.getQuantity();
	}
	
	
	@EventSourcingHandler
	public void on(ProductReservedEvent productReservedEvent) {
		this.quantity -= productReservedEvent.getQuantity();
	}
	
	

}
