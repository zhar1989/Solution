package net.zaycev.Solution.model;

/**
 * Created by evgeny on 01.02.19.
 * Исключение, используемое для валидации
 */
public class ValidationException extends Exception {
    public ValidationException() { super(); }
    public ValidationException(String message) { super(message); }
    public ValidationException(String message, Throwable cause) { super(message, cause); }
    public ValidationException(Throwable cause) { super(cause); }
}
