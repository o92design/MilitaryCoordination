package com.military.coordination.model;

public record IntelReport(String id, String content, String timestamp) {
    public IntelReport {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        if (timestamp == null || timestamp.trim().isEmpty()) {
            throw new IllegalArgumentException("Timestamp cannot be null or empty");
        }

        // Normalize
        id = id.trim();
        content = content.trim();
        timestamp = timestamp.trim();
    }
}
