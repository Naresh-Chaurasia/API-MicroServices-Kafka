package com.appsdeveloperblog.estore.ProductsService.controllers;

//import com.pluralsight.kafka.producer.Main;
import org.axonframework.commandhandling.gateway.CommandGateway;
import com.appsdeveloperblog.estore.ProductsService.command.CreateProductCommand;
import com.appsdeveloperblog.estore.ProductsService.command.rest.CreateProductRestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

//import static com.pluralsight.kafka.producer.Main.*;

@RestController
@RequestMapping("/products")
public class ProductsCommandController {

    private final Environment env;
    private final CommandGateway commandGateway;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsCommandController.class);

    @Autowired
    public ProductsCommandController(Environment env, CommandGateway commandGateway) {
        this.env = env;
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createProducts(@Valid @RequestBody CreateProductRestModel createProductRestModel) throws InterruptedException {

        LOGGER.info("-------------------------ProductsCommandController/createProducts()-------------------------");

        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .price(createProductRestModel.getPrice())
                .quantity(createProductRestModel.getQuantity())
                .title(createProductRestModel.getTitle())
                .productId(UUID.randomUUID().toString()).build();

        LOGGER.info("-------------------------ProductsCommandController/createProducts()/calls commandGateway.sendAndWait(createProductCommand)-------------------------");
        String returnValue = commandGateway.sendAndWait(createProductCommand);

        //return "postProducts on Port:::" + env.getProperty("local.server.port") + createProductRestModel.getTitle();
        return returnValue;

    }

    /*
    @GetMapping
    public String getProduct() {
        return "getProduct on Port:::" + env.getProperty("local.server.port");
    }

    @PutMapping
    public String updateProduct() {
        return "updateProduct on Port:::" + env.getProperty("local.server.port");
    }

    @DeleteMapping
    public String deleteProduct() {
        return "deleteProduct on Port:::" + env.getProperty("local.server.port");
    }*/

}
