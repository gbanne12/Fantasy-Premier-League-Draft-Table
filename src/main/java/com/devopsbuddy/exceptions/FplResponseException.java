package com.devopsbuddy.exceptions;

import java.io.IOException;

public class FplResponseException extends IOException {

    public FplResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
