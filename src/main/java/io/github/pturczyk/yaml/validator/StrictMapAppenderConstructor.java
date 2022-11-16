package io.github.pturczyk.yaml.validator;

import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.parser.ParserException;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

/**
 * Allows stricter validation in line with https://github.com/spring-projects/spring-boot/issues/1683
 * @author mrohland
 * @since 04.04.16
 */
public class StrictMapAppenderConstructor extends Constructor {

    public StrictMapAppenderConstructor(Class<?> theRoot) {
        super(theRoot);
    }

    public StrictMapAppenderConstructor() {
        super();
    }

    @Override
    protected Map<Object, Object> constructMapping(MappingNode node) {
        try {
            return super.constructMapping(node);
        } catch (IllegalStateException e) {
            throw new ParserException("while parsing MappingNode", node.getStartMark(), e.getMessage(), node.getEndMark());
        }
    }

    @Override
    protected Map<Object, Object> createDefaultMap() {
        final Map<Object, Object> delegate = super.createDefaultMap();
        return new AbstractMap<Object, Object>() {
            @Override
            public Object put(Object key, Object value) {
                if (delegate.containsKey(key)) {
                    throw new IllegalStateException("duplicate key: " + key);
                }
                return delegate.put(key, value);
            }
            @Override
            public Set<Entry<Object, Object>> entrySet() {
                return delegate.entrySet();
            }
        };
    }

}