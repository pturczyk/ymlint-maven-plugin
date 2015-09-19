package com.github.pturczyk.yaml.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * YAML file utilities
 *
 * @author pturczyk@gmail.com
 */
public final class YamlFileUtils {
    private static final String[] YAML_EXTENSIONS = {"yml", "yaml"};

    private YamlFileUtils() {
        // utility class, do not instantiate
    }

    /**
     * Retrieves YAML files from the provided locations
     *
     * @param yamlPaths to scan for YAML files
     * @return found yaml files
     */
    public static Set<String> getFiles(List<String> yamlPaths) {
        Set<String> result = new HashSet<>();

        for (String yamlPath : yamlPaths) {
            File file = new File(yamlPath);
            if (file.exists()) {
                if (file.isDirectory()) {
                    Collection<File> yamlFiles = FileUtils.listFiles(file, YAML_EXTENSIONS, true);
                    for (File yamlFile : yamlFiles) {
                        result.add(yamlFile.getAbsolutePath());
                    }
                } else {
                    result.add(file.getAbsolutePath());
                }
            }
        }

        return result;
    }
}
