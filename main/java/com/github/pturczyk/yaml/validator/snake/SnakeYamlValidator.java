package com.github.pturczyk.yaml.validator.snake;

import com.github.pturczyk.yaml.validator.ValidationException;
import com.github.pturczyk.yaml.validator.YamlValidator;
import org.yaml.snakeyaml.composer.Composer;
import org.yaml.snakeyaml.error.MarkedYAMLException;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.reader.UnicodeReader;
import org.yaml.snakeyaml.resolver.Resolver;

import javax.inject.Singleton;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * YAML validator implementation based on the Snake YAML Library.
 *
 * @author pturczyk@gmail.com
 */
@Singleton
public class SnakeYamlValidator implements YamlValidator {

    @Override
    public void validate(String yamlPath) throws ValidationException, IOException {
        try (FileInputStream YamlReader = new FileInputStream(yamlPath)) {

            StreamReader streamReader = new StreamReader(new UnicodeReader(YamlReader));
            Composer composer = new Composer(new ParserImpl(streamReader), new Resolver());

            doValidate(composer);
        }
    }

    private void doValidate(Composer composer) throws ValidationException {
        try {
            // naive approach iterating over each document and parsing it
            // if no exceptions are thrown document is considered valid
            while (composer.checkNode()) {
                composer.getNode();
            }
        } catch (MarkedYAMLException exception) {
            throw new ValidationException(exception.getMessage(), exception);
        }
    }
}
