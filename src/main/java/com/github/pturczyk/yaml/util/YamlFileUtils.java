package com.github.pturczyk.yaml.util;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.io.FileUtils.listFiles;

/**
 * YAML file utilities
 *
 * @author pturczyk@gmail.com
 */
@Named
@Singleton
public class YamlFileUtils {
    private static final String[] YAML_EXTENSIONS = {"yml", "yaml"};

    /**
     * Retrieves absolute YAML file paths from the provided locations
     *
     * @param paths to scan for YAML files
     *
     * @return absolute paths of found yaml files
     */
    public Set<String> getAbsoluteFilePaths(Set<String> paths) {
        Set<String> result = new HashSet<>();

        for (String path : paths) {
            File file = new File(path);
            if (file.exists()) {
                if (file.isDirectory()) {
                    for (File yamlFile : listFiles(file, YAML_EXTENSIONS, true)) {
                        result.add(yamlFile.getAbsolutePath());
                    }
                } else {
                    result.add(file.getAbsolutePath());
                }
            }
        }

        return result;
    }

    /**
     * Opens stream for the provided file
     *
     * @param filePath path to file to open stream for
     *
     * @return {@link InputStream}
     */
    public InputStream openStream(String filePath) throws FileNotFoundException {
        return new FileInputStream(filePath);
    }

}
