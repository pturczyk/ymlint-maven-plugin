package com.github.pturczyk.yaml.validator;

import java.io.IOException;
import java.io.InputStream;

/**
 * YAML file validator
 *
 * @author pturczyk@gmail.com.
 */
public interface YamlValidator {
    /**
     * Validates the provided yaml stream
     *
     * @param inputStream yaml stream
     * @param strict set to true for strict duplicate checking
     *
     * @throws ValidationException   when validation fails
     * @throws IOException if any problem occurs while reading the input stream
     */
    void validate(InputStream inputStream, boolean strict) throws ValidationException, IOException;
}
