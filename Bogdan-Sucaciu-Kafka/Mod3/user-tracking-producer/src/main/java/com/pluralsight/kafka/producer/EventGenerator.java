package com.pluralsight.kafka.producer;

import com.github.javafaker.Faker;
import com.pluralsight.kafka.producer.enums.Color;
import com.pluralsight.kafka.producer.enums.ProductType;
import com.pluralsight.kafka.producer.enums.DesignType;
import com.pluralsight.kafka.producer.enums.UserId;
import com.pluralsight.kafka.producer.model.Event;
import com.pluralsight.kafka.producer.model.Product;
import com.pluralsight.kafka.producer.model.User;


public class EventGenerator {

    private Faker faker = new Faker();

    public Event generateEvent() {
        return Event.builder()
                .user(generateRandomUser())
                .product(generateRandomObject())
                .build();
    }

    private User generateRandomUser() {
        return User.builder()
                .userId(faker.options().option(UserId.class))
                .username(faker.name().lastName())
                .dateOfBirth(faker.date().birthday())
                .build();
    }

    private Product generateRandomObject() {
        return Product.builder()
                .color(faker.options().option(Color.class))
                .type(faker.options().option(ProductType.class))
                .designType(faker.options().option(DesignType.class))
                .build();
    }
}
