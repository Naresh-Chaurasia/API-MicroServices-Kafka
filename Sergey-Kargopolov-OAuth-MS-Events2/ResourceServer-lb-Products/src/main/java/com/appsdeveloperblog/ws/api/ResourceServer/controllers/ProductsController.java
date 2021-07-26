package com.appsdeveloperblog.estore.ProductsService.controllers;

//import com.pluralsight.kafka.producer.Main;
import com.appsdeveloperblog.estore.ProductsService.command.CreateProductCommand;
import com.appsdeveloperblog.estore.ProductsService.command.rest.CreateProductRestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

//import static com.pluralsight.kafka.producer.Main.*;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final Environment env;
    private final CommandGateway commandGateway;

    @Autowired
    public ProductsController(Environment env, CommandGateway commandGateway) {
        this.env = env;
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createProducts(@RequestBody CreateProductRestModel createProductRestModel) throws InterruptedException {

        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .price(createProductRestModel.getPrice())
                .quantity(createProductRestModel.getQuantity())
                .title(createProductRestModel.getTitle())
                .productId(UUID.randomUUID().toString()).build();

        String returnValue = commandGateway.sendAndWait(createProductCommand);

        //return "postProducts on Port:::" + env.getProperty("local.server.port") + createProductRestModel.getTitle();
        return returnValue;

    }

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
    }

}
