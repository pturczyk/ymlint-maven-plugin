package com.github.pturczyk.yaml.validator;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.*;

/**
 * @author pturczyk@gmail.com
 */
public class SnakeYamlValidatorTest {

    private static final String VALID_YAML =
            "valid.first: value\n" +
            "--- \n" +
            "valid.second: value\n";

    private static final String INVALID_YAML =
            "valid.first: value\n" +
            "--- \n" +
            "invalid.yaml: '";

    private SnakeYamlValidator validator = new SnakeYamlValidator();

    @Test
    public void testShouldPassYamlValidation() {
        // given
        final InputStream stream = getStream(VALID_YAML);

        // when
        Throwable caught = catchThrowable(new ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                validator.validate(stream);
            }
        });

        // then
        assertThat(caught).isNull();
    }

    @Test
    public void testShouldFailYamlValidation() {
        // given
        final InputStream stream = getStream(INVALID_YAML);

        // when
        Throwable caught = catchThrowable(new ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                validator.validate(stream);
            }
        });

        // then
        assertThat(caught).isInstanceOf(ValidationException.class);
    }

    private InputStream getStream(String yaml) {
        return new ByteArrayInputStream(yaml.getBytes());
    }

}