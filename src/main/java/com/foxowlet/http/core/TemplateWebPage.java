package com.foxowlet.http.core;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateWebPage extends AbstractWebPage {
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\$\\{ *([a-z0-9-]+) *}");
    private final String filePath;
    private final Map<String, String> placeholderValues;

    public TemplateWebPage(String filePath, Map<String, String> placeholderValues) {
        super();
        this.filePath = filePath;
        this.placeholderValues = placeholderValues;
    }

    public TemplateWebPage(String filePath, Charset charset, Map<String, String> placeholderValues) {
        super(charset);
        this.filePath = filePath;
        this.placeholderValues = placeholderValues;
    }

    @Override
    public byte[] getBytes() {
        try {
            String template = Files.readString(Path.of(filePath));
            Matcher matcher = PLACEHOLDER_PATTERN.matcher(template);
            StringBuilder sb = new StringBuilder();
            while (matcher.find()) {
                String placeholderName = matcher.group(1);
                String value = placeholderValues.getOrDefault(placeholderName, "");
                matcher.appendReplacement(sb, value);
            }
            matcher.appendTail(sb);
            return sb.toString().getBytes(getCharset());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
