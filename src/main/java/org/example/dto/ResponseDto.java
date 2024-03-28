package org.example.dto;

import java.util.Objects;

public class ResponseDto {

    private String status;
    private String errorMessage;

    public ResponseDto() {}

    public ResponseDto(String status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public static ResponseDto badRequest(String errorMessage) {
        return new ResponseDto("BAD_REQUEST", errorMessage);
    }

    public static ResponseDto ok() {
        return new ResponseDto("OK", "");
    }

    public String getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseDto that = (ResponseDto) o;
        return Objects.equals(status, that.status) && Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, errorMessage);
    }
}
