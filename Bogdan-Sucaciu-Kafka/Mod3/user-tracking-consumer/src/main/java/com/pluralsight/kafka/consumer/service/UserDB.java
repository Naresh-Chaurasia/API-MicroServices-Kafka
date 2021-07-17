package com.pluralsight.kafka.consumer.service;

import com.pluralsight.kafka.consumer.enums.UserId;
import com.pluralsight.kafka.consumer.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserDB {

    private static Map<String, User> users = new HashMap<>();

    static {
        users.put(UserId.ABC123.toString(), new User(UserId.ABC123));
        users.put(UserId.ABC321.toString(), new User(UserId.ABC321));
        users.put(UserId.CBA123.toString(), new User(UserId.CBA123));
        users.put(UserId.CBA321.toString(), new User(UserId.CBA321));
        users.put(UserId.A1B2C3.toString(), new User(UserId.A1B2C3));
    }

    public User findByID(String id) {
        return users.get(id);
    }

    public void save(User user) {
        users.put(user.getUserId().toString(), user);
    }
}
