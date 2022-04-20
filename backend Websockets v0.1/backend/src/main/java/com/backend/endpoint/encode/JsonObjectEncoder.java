package com.backend.endpoint.encode;

import org.json.JSONObject;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class JsonObjectEncoder implements Encoder.Text<JSONObject> {
    @Override
    public String encode(JSONObject jsonObject) throws EncodeException {
        return jsonObject.toString();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }
}
