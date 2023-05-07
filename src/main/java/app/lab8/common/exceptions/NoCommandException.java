package app.lab8.common.exceptions;

public class NoCommandException extends RuntimeException {
    public NoCommandException(String message) {
        System.out.println(message);
    }
}