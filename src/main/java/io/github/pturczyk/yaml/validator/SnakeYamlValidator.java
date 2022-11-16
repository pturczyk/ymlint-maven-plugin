package io.github.pturczyk.yaml.validator;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.MarkedYAMLException;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.InputStream;

/**
 * YAML validator implementation based on the Snake YAML Library.
 *
 * @author pturczyk@gmail.com
 */
@Named
@Singleton
public class SnakeYamlValidator implements YamlValidator {

    @Override
    public void validate(InputStream yamlStream, boolean strict) throws ValidationException {

        try {
            if (strict) {
                for (Object obj : new Yaml(new StrictMapAppenderConstructor()).loadAll(yamlStream)) {/*noop*/}
            } else {
                for (Object obj : new Yaml().loadAll(yamlStream)) {/*noop*/}
            }
        } catch (MarkedYAMLException exception) {
            throw new ValidationException(exception.getMessage(), exception);
        }
    }
}
