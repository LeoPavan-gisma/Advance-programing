package com.gisma.pams.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorDetails {
    private String errorCode;
    private String message;
    private String details;
    private String path;
    private Integer status;
    private LocalDateTime timestamp;
    private String requestId;

    public ErrorDetails() {
    }

    public ErrorDetails(String errorCode, String message, String details, String path,
                        Integer status, LocalDateTime timestamp, String requestId) {
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
        this.path = path;
        this.status = status;
        this.timestamp = timestamp;
        this.requestId = requestId;
    }

    public ErrorDetails(String errorCode, String message, String path, Integer status) {
        this.errorCode = errorCode;
        this.message = message;
        this.path = path;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String toFormattedString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] %s - %s (HTTP %d) - Path: %s",
                timestamp.format(formatter),
                errorCode,
                message,
                status,
                path);
    }
}
