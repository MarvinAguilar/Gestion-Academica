package com.backend.utils;

import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

public class requestToJson {
    public static JSONObject getJsonRequest(HttpServletRequest request) throws IOException {
        return new JSONObject(request
                .getReader()
                .lines()
                .collect(Collectors.joining()));
    }
}
