package com.longx.intelligent.lib.ltxt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by LONG on 2026/8/11 at 23:27.
 */
public class LtxtEditor {
    private final Map<String, String> headers;
    private final StringBuilder bodyBuilder;

    public LtxtEditor() {
        this.headers = new LinkedHashMap<String, String>();
        this.bodyBuilder = new StringBuilder();
    }

    public LtxtEditor(LtxtDocument document) {
        this();
        if (document != null) {
            this.headers.putAll(document.getHeaders());
            this.bodyBuilder.append(document.getBody());
        }
    }

    public static LtxtEditor create() {
        return new LtxtEditor();
    }

    public static LtxtEditor from(String text) {
        return new LtxtEditor(LtxtParser.parse(text));
    }

    public static LtxtEditor from(File file) throws IOException {
        return new LtxtEditor(LtxtParser.parse(file));
    }

    public static LtxtEditor from(Path path) throws IOException {
        return new LtxtEditor(LtxtParser.parse(path));
    }

    public static LtxtEditor from(InputStream inputStream) throws IOException {
        return new LtxtEditor(LtxtParser.parse(inputStream));
    }

    public LtxtEditor putHeader(String key, String value) {
        if (key != null && value != null) {
            headers.put(key.trim(), value.trim());
        }
        return this;
    }

    public LtxtEditor putHeader(String key, int value) {
        return putHeader(key, String.valueOf(value));
    }

    public LtxtEditor putHeader(String key, long value) {
        return putHeader(key, String.valueOf(value));
    }

    public LtxtEditor putHeader(String key, float value) {
        return putHeader(key, String.valueOf(value));
    }

    public LtxtEditor putHeader(String key, double value) {
        return putHeader(key, String.valueOf(value));
    }

    public LtxtEditor putHeader(String key, boolean value) {
        return putHeader(key, String.valueOf(value));
    }

    public LtxtEditor putHeader(String key, Object value) {
        if (key != null && value != null) {
            putHeader(key, String.valueOf(value));
        }
        return this;
    }

    public LtxtEditor removeHeader(String key) {
        headers.remove(key);
        return this;
    }

    public LtxtEditor clearHeaders() {
        headers.clear();
        return this;
    }

    public LtxtEditor setBody(String body) {
        this.bodyBuilder.setLength(0);
        if (body != null) {
            this.bodyBuilder.append(body);
        }
        return this;
    }

    public LtxtEditor appendBody(String text) {
        if (text != null) {
            this.bodyBuilder.append(text);
        }
        return this;
    }

    public LtxtEditor appendBodyLine(String text) {
        if (bodyBuilder.length() > 0) {
            this.bodyBuilder.append("\n");
        }
        if (text != null) {
            this.bodyBuilder.append(text);
        }
        return this;
    }

    public LtxtEditor clearBody() {
        this.bodyBuilder.setLength(0);
        return this;
    }

    public String toStr() {
        StringBuilder sb = new StringBuilder();
        String bodyString = bodyBuilder.toString();
        boolean needsEmptyHeaderGuard = false;

        if (headers.isEmpty()) {
            try (BufferedReader reader = new BufferedReader(new StringReader(bodyString))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String trimmed = line.trim();
                    if (trimmed.length() > 0) {
                        if (trimmed.equalsIgnoreCase("!header")) {
                            needsEmptyHeaderGuard = true;
                        }
                        break;
                    }
                }
            } catch (IOException ignored) {
            }
        }

        if (!headers.isEmpty() || needsEmptyHeaderGuard) {
            sb.append("!header\n");
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            sb.append("\n");
        }
        sb.append(bodyString);
        return sb.toString();
    }

    public LtxtDocument toDocument() {
        return new LtxtDocument(new LinkedHashMap<String, String>(headers), bodyBuilder.toString());
    }

    public void save(File file) throws IOException {
        if (file == null) return;
        try (FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8")) {
            osw.write(toStr());
            osw.flush();
        }
    }

    public void save(Path path) throws IOException {
        if (path != null) {
            save(path.toFile());
        }
    }

    public void save(OutputStream outputStream) throws IOException {
        if (outputStream != null) {
            outputStream.write(toStr().getBytes("UTF-8"));
            outputStream.flush();
        }
    }
}