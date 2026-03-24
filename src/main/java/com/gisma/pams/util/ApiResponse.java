package com.gisma.pams.util;

public class ApiResponse<T> {
    private String message;
    private T data;
    private String status;
    private Integer statusCode;
    private Long timestamp;

    public ApiResponse() {
    }

    public ApiResponse(String message, T data, String status, Integer statusCode, Long timestamp) {
        this.message = message;
        this.data = data;
        this.status = status;
        this.statusCode = statusCode;
        this.timestamp = timestamp;
    }

    public ApiResponse(String message, T data, String status) {
        this.message = message;
        this.data = data;
        this.status = status;
        this.timestamp = System.currentTimeMillis();
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getStatusCode() { return statusCode; }
    public void setStatusCode(Integer statusCode) { this.statusCode = statusCode; }
    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, data, "success", 200, System.currentTimeMillis());
    }

    public static <T> ApiResponse<T> created(String message, T data) {
        return new ApiResponse<>(message, data, "success", 201, System.currentTimeMillis());
    }

    public static <T> ApiResponse<T> error(String message, Integer statusCode) {
        return new ApiResponse<>(message, null, "error", statusCode, System.currentTimeMillis());
    }
}
