package com.demo_bank_v1.helpers;

import java.util.UUID;

public class Token {

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

}
