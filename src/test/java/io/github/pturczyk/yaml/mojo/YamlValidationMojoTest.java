package io.github.pturczyk.yaml.mojo;

import io.github.pturczyk.yaml.util.YamlFileUtils;
import io.github.pturczyk.yaml.validator.ValidationException;
import io.github.pturczyk.yaml.validator.YamlValidator;
import org.apache.maven.plugin.MojoFailureException;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * @author pturczyk@gmail.com
 */
@ExtendWith(MockitoExtension.class)
public class YamlValidationMojoTest {

    @Mock
    private YamlValidator validator;

    @Mock
    private YamlFileUtils fileUtils;

    @InjectMocks
    private YamlValidationMojo yamlValidationMojo;

    @Test
    void testExecutionShouldContinueOnFailure()
            throws MojoFailureException, IOException, ValidationException {

        // given
        Set<String> yamlPaths = Sets.newSet("/path/file1.yml", "/path/file2.yml");
        given(fileUtils.getAbsoluteFilePaths(anySetOf(String.class))).willReturn(yamlPaths);

        InputStream mockedInputStream = mock(InputStream.class);
        given(fileUtils.openStream(anyString())).willReturn(mockedInputStream);

        ValidationException exception = new ValidationException("", null);
        doThrow(exception).when(validator).validate(any(InputStream.class), anyBoolean());

        // when
        yamlValidationMojo.execute();

        // then
        verify(validator, times(2)).validate(any(InputStream.class), anyBoolean());
    }

    @Test
    void testExecutionShouldStopOnFailure()
            throws MojoFailureException, IOException, ValidationException {

        // given
        Set<String> yamlPaths = Sets.newSet("/path/file1.yml", "/path/file2.yml");
        given(fileUtils.getAbsoluteFilePaths(anySetOf(String.class))).willReturn(yamlPaths);

        InputStream mockedInputStream = mock(InputStream.class);
        given(fileUtils.openStream(anyString())).willReturn(mockedInputStream);

        ValidationException exception = new ValidationException("", null);
        doThrow(exception).when(validator).validate(any(InputStream.class), anyBoolean());

        Whitebox.setInternalState(yamlValidationMojo, "failOnError", true);

        // when
        Throwable caught = catchThrowable(new ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                yamlValidationMojo.execute();
            }
        });

        // then
        assertThat(caught).isInstanceOf(MojoFailureException.class).hasCauseInstanceOf(ValidationException.class);
        verify(validator).validate(any(InputStream.class), anyBoolean());
    }

}