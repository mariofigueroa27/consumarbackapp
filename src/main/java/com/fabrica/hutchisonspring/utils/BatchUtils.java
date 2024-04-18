package com.fabrica.hutchisonspring.utils;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public final class BatchUtils {

    private static final String MESSAGE_KEY = "message";
    private static final String PATH_KEY = "filePath";
    private static final String START_KEY = "start";
    private static final String END_KEY = "end";

    public static Map<String, Object> createBatchResponse(String applicationName, String entityName) {
        Map<String, Object> response = new HashMap<>();
        response.put(MESSAGE_KEY, applicationName + "." + entityName + ".bulk.error.file");
        return response;
    }

    public static Map<String, Object> createBatchResponse(String applicationName, String entityName, String filePath, Instant start) {
        Map<String, Object> response = new HashMap<>();
        response.put(MESSAGE_KEY, applicationName + "." + entityName + (filePath.contains("-ok") ? ".bulk.success" : ".bulk.error"));
        response.put(PATH_KEY, filePath);
        response.put(START_KEY, start);
        response.put(END_KEY, Instant.now());
        return response;
    }
}
