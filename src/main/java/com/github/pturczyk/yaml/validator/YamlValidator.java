package com.github.pturczyk.yaml.validator;

import java.io.IOException;

/**
 * YAML file validator
 *
 * @author pturczyk@gmail.com.
 */
public interface YamlValidator {
    /**
     * Validates the provided YAML file
     *
     * @param yamlPath path to YAML file to validate
     *
     * @throws ValidationException   when validation fails
     * @throws IOException if any problem occurs while reading YAML file
     */
    void validate(String yamlPath) throws ValidationException, IOException;
}
