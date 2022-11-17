Travis CI [![Build Status](https://travis-ci.org/pturczyk/ymlint-maven-plugin.png?branch=master)](https://travis-ci.org/pturczyk/ymlint-maven-plugin) 

# YAML lint maven plugin 
Performs YAML files validation using the `org.yaml.snakeyaml` library

### Using the plugin
In order to enable the YAML file validation add the following section to your pom.xml file

```xml
<plugin>
    <groupId>io.github.pturczyk</groupId>
    <artifactId>ymlint-maven-plugin</artifactId>
    <version>...</version>
    
    <!-- optional -->
    <configuration>
        <yamlPaths>
          <!-- include src/main/resources/configuration.yml --> 
          <yamlPath>src/main/resources/configuration.yml</ymlPath>
          <!-- include all yaml files from src/test/resources directory and subdirectories -->
          <yamlPath>src/test/resources</ymlPath> 
        </yamlPaths>
    </configuration>
    
   <executions>
        <execution>
            <phase>validate</phase>
            <goals>
                <goal>check</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
