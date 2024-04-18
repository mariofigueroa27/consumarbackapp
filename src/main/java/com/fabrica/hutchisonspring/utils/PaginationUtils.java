package com.fabrica.hutchisonspring.utils;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;


public final class PaginationUtils {
    private static final String HEADER_X_TOTAL_COUNT = "X-Total-Count";

    public static <T> HttpHeaders generatePaginationHeaders(Page<T> page) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_X_TOTAL_COUNT, Long.toString(page.getTotalElements()));
        return headers;
    }
}
