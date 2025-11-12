package voldemort.store;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import voldemort.cluster.Node;
import voldemort.routing.RoutingStrategy;
import voldemort.serialization.Serializer;
import voldemort.serialization.SerializerDefinition;
import voldemort.serialization.SerializerFactory;
import voldemort.utils.ByteArray;
import voldemort.versioning.Version;
import voldemort.versioning.Versioned;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import voldemort.utils.ClosableIterator;
import voldemort.utils.Pair;

public class StoreUtils {

    private static Logger logger = Logger.getLogger(StoreUtils.class);

    static public void assertValidKeys(Iterable<?> keys) {
        if (keys == null)
            throw new IllegalArgumentException("Keys cannot be null.");
        for (Object key : keys) assertValidKey(key);
    }

    static public <K> void assertValidKey(K key) {
        if (key == null)
            throw new IllegalArgumentException("Key cannot be null.");
    }

    static public <K, V> List<Versioned<V>> get(Store<K, V> storageEngine, K key) {
        Map<K, List<Versioned<V>>> result = storageEngine.getAll(Collections.singleton(key));
        if (result.size() > 0)
            return result.get(key);
        else
            return Collections.emptyList();
    }

    static public <K, V> Map<K, List<Versioned<V>>> getAll(Store<K, V> storageEngine, Iterable<K> keys) {
        Map<K, List<Versioned<V>>> result = newEmptyHashMap(keys);
        for (K key : keys) {
            List<Versioned<V>> value = storageEngine.get(key);
            if (!value.isEmpty())
                result.put(key, value);
        }
        return result;
    }

    static public <K, V> HashMap<K, V> newEmptyHashMap(Iterable<?> iterable) {
        if (iterable instanceof Collection<?>)
            return Maps.newHashMapWithExpectedSize(((Collection<?>) iterable).size());
        return Maps.newHashMap();
    }

    static public void close(InputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                logger.error("Error closing stream", e);
            }
        }
    }

    static public void assertValidMetadata(ByteArray key, RoutingStrategy routingStrategy, int currentNodeId) {
        List<Node> nodes = routingStrategy.routeRequest(key.get());
        for (Node node : nodes) {
            if (node.getId() == currentNodeId) {
                return;
            }
        }
        throw new InvalidMetadataException("client routing strategy not in sync with store routing strategy!");
    }

    static public <V> List<Version> getVersions(List<Versioned<V>> versioneds) {
        List<Version> versions = Lists.newArrayListWithCapacity(versioneds.size());
        for (Versioned<?> versioned : versioneds) versions.add(versioned.getVersion());
        return versions;
    }

    @SuppressWarnings("unchecked")
    static public <T> Serializer<T> unsafeGetSerializer(SerializerFactory serializerFactory, SerializerDefinition serializerDefinition) {
        return (Serializer<T>) serializerFactory.getSerializer(serializerDefinition);
    }

    static public StoreDefinition getStoreDef(List<StoreDefinition> list, String name) {
        for (StoreDefinition def : list) if (def.getName().equals(name))
            return def;
        return null;
    }

    static public <K, V> ClosableIterator<K> keys(final ClosableIterator<Pair<K, V>> values) {
        return new ClosableIterator<K>() {

            public void close() {
                values.close();
            }

            public boolean hasNext() {
                return values.hasNext();
            }

            public K next() {
                Pair<K, V> value = values.next();
                if (value == null)
                    return null;
                return value.getFirst();
            }

            public void remove() {
                values.remove();
            }
        };
    }
}
