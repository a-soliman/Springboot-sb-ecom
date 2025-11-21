package com.ecommerce.project.utils;

import java.util.UUID;

public class IdUtils {
    public static String generateId(String prefix) {
        return prefix + UUID.randomUUID().toString();
    }
}
