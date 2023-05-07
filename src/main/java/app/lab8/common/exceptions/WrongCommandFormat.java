package app.lab8.common.exceptions;

public class WrongCommandFormat extends RuntimeException{
    public WrongCommandFormat(String message) {
        System.out.println(message);
    }
}
