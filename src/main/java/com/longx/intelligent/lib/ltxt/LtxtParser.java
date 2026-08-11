package com.longx.intelligent.lib.ltxt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by LONG on 2026/8/11 at 22:28.
 */
public class LtxtParser {
    public static LtxtDocument parse(String text) {
        if (text == null || text.length() == 0) {
            return new LtxtDocument(new LinkedHashMap<String, String>(), "");
        }
        try (BufferedReader reader = new BufferedReader(new StringReader(text))) {
            return parse(reader);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to parse ltxt content", e);
        }
    }

    public static LtxtDocument parse(File file) throws IOException {
        if (file == null || !file.exists()) {
            return new LtxtDocument(new LinkedHashMap<String, String>(), "");
        }
        try (InputStream is = new FileInputStream(file)) {
            return parse(is);
        }
    }

    public static LtxtDocument parse(Path path) throws IOException {
        return path != null ? parse(path.toFile()) : new LtxtDocument(new LinkedHashMap<String, String>(), "");
    }

    public static LtxtDocument parse(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return new LtxtDocument(new LinkedHashMap<String, String>(), "");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
            return parse(reader);
        }
    }

    public static LtxtDocument parse(BufferedReader reader) throws IOException {
        Map<String, String> headers = new LinkedHashMap<String, String>();
        StringBuilder bodyBuilder = new StringBuilder();
        String line;
        boolean inHeaderArea = false;
        boolean headerFinished = false;
        while ((line = reader.readLine()) != null) {
            String trimmedLine = line.trim();
            if (!headerFinished) {
                if (!inHeaderArea) {
                    if (trimmedLine.length() > 0) {
                        if (trimmedLine.equalsIgnoreCase("!header")) {
                            inHeaderArea = true;
                            continue;
                        } else {
                            headerFinished = true;
                        }
                    }
                } else {
                    if (trimmedLine.length() == 0) {
                        headerFinished = true;
                        continue;
                    }
                    int colonIndex = line.indexOf(':');
                    if (colonIndex != -1) {
                        String key = line.substring(0, colonIndex).trim();
                        String value = line.substring(colonIndex + 1).trim();
                        headers.put(key, value);
                    }
                    continue;
                }
            }
            if (bodyBuilder.length() > 0) {
                bodyBuilder.append("\n");
            }
            bodyBuilder.append(line);
        }
        return new LtxtDocument(headers, bodyBuilder.toString());
    }
}