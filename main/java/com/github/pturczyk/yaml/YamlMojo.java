package com.github.pturczyk.yaml;

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
import java.util.List;

/**
 * YAML validating mojo
 *
 * @author pturczyk@gmail.com
 */
@Mojo(name = "validate", defaultPhase = LifecyclePhase.VALIDATE)
public class YamlMojo extends AbstractMojo {

    /**
     * Paths to YAML files. If the path points to a directory, the directory will be
     * recursively scanned for YAML files. By default plugin will scan the current directory.
     */
    @Parameter(property = "yamlPaths", defaultValue = ".")
    private List<String> yamlPaths;

    /**
     * Tells whether build should continue in case of validation errors. By default its set to true.
     */
    @Parameter(property = "failOnError", defaultValue = "true")
    private boolean failOnError;

    @Inject
    private YamlValidator yamlLint;

    @Override
    public void execute() throws MojoFailureException {
        for (String yamlFile : YamlFileUtils.getFiles(yamlPaths)) {
            getLog().info("Validating " + yamlFile);
            validate(yamlFile);
        }
    }

    private void validate(String yamlFile) throws MojoFailureException {
        try {
            yamlLint.validate(yamlFile);
        } catch (ValidationException | IOException e) {
            logError(yamlFile, e);
            failIfRequired(e);
        }
    }

    private void logError(String yamlFile, Exception exception) {
        String error = String.format("'%s' validation failed: %s", yamlFile, exception.getMessage());
        getLog().error(error);
        getLog().debug(error, exception);
    }

    private void failIfRequired(Exception e) throws MojoFailureException {
        if (failOnError) {
            throw new MojoFailureException("YAML validation failed", e);
        }
    }

}