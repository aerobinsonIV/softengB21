package edu.wpi.cs3733.g.responses;

public class GenericResponse {
    int statusCode;
    String message;

    public GenericResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("{statusCode=%d,message=%s}", statusCode, message);
    }
}