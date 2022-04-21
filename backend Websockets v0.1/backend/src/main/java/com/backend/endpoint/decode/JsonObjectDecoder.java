package com.backend.endpoint.decode;

import com.backend.endpoint.encode.JsonObjectEncoder;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonObjectDecoder implements Decoder.Text<JSONObject> {

    @Override
    public JSONObject decode(String s) {
        return new JSONObject(s);
    }

    @Override
    public boolean willDecode(String s) {
        try {
            new JSONObject(s);
            return true;
        } catch (JSONException ex) {
            return false;
        }
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

}