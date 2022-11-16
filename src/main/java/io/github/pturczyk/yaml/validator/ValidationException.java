package io.github.pturczyk.yaml.validator;

/**
 * Validation exception
 *
 * @author pturczyk@gmail.com
 */
public class ValidationException extends Exception {

    public ValidationException(String message, Exception cause) {
        super(message, cause);
    }
}
