package com.github.pturczyk.yaml.mojo;

import com.github.pturczyk.yaml.util.YamlFileUtils;
import com.github.pturczyk.yaml.validator.ValidationException;
import com.github.pturczyk.yaml.validator.YamlValidator;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * YAML validating MOJO
 *
 * @author pturczyk@gmail.com
 */
@Mojo(name = "check", defaultPhase = LifecyclePhase.VALIDATE, requiresProject = false)
public class YamlValidationMojo extends AbstractMojo {

    /**
     * Paths to YAML files. If the path points to a directory, the directory will be
     * recursively scanned for YAML files. By default plugin will scan the current directory.
     */
    @Parameter(property = "yamlPaths", defaultValue = ".")
    private Set<String> yamlPaths;

    /**
     * Tells whether build should continue in case of validator errors. By default its set to true.
     */
    @Parameter(property = "failOnError", defaultValue = "true")
    private boolean failOnError;

    @Inject
    private YamlValidator validator;

    @Inject
    private YamlFileUtils fileUtils;

    @Override
    public void execute() throws MojoFailureException {
        for (String yamlFilePath : fileUtils.getAbsoluteFilePaths(yamlPaths)) {
            getLog().info("Validating " + yamlFilePath);
            validate(yamlFilePath);
        }
    }

    private void validate(String yamlFilePath) throws MojoFailureException {
        try (InputStream stream = fileUtils.openStream(yamlFilePath)) {
            validator.validate(stream);
        } catch (ValidationException | IOException e) {
            logError(yamlFilePath, e);
            failIfRequired(e);
        }
    }

    private void logError(String yamlFilePath, Exception exception) {
        String error = String.format("'%s' validator failed: %s", yamlFilePath, exception.getMessage());
        getLog().error(error);
        getLog().debug(error, exception);
    }

    private void failIfRequired(Exception e) throws MojoFailureException {
        if (failOnError) {
            throw new MojoFailureException("YAML validator failed", e);
        }
    }

}