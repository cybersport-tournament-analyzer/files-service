package com.gpoint.files_service.exception;

public class S3Exception extends RuntimeException {
    public S3Exception(String message) {
        super(message);
    }

    public S3Exception(Throwable cause) {
        super(cause);
    }
}