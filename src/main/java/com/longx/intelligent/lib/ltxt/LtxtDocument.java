package com.longx.intelligent.lib.ltxt;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class LtxtDocument {
    public static final String EXTENSION = "ltxt";
    private final Map<String, String> headers;
    private final String body;

    public LtxtDocument(Map<String, String> headers, String body) {
        this.headers = headers != null ? headers : new LinkedHashMap<String, String>();
        this.body = body != null ? body : "";
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getHeaderOrDefault(String key, String defaultValue) {
        String value = headers.get(key);
        return value != null ? value : defaultValue;
    }

    public String getHeader(String key, String defaultValue) {
        return getHeaderOrDefault(key, defaultValue);
    }

    public int getHeader(String key, int defaultValue) {
        String val = getHeader(key);
        if (val == null) return defaultValue;
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public long getHeader(String key, long defaultValue) {
        String val = getHeader(key);
        if (val == null) return defaultValue;
        try {
            return Long.parseLong(val);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public float getHeader(String key, float defaultValue) {
        String val = getHeader(key);
        if (val == null) return defaultValue;
        try {
            return Float.parseFloat(val);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public double getHeader(String key, double defaultValue) {
        String val = getHeader(key);
        if (val == null) return defaultValue;
        try {
            return Double.parseDouble(val);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public boolean getHeader(String key, boolean defaultValue) {
        String val = getHeader(key);
        if (val == null) return defaultValue;
        return Boolean.parseBoolean(val);
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "LtxtDocument{" +
                "headers=" + headers +
                ", bodyLength=" + body.length() +
                '}';
    }
}