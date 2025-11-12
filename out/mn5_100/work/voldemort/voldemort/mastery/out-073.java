package voldemort.store.nonblockingstore;

import voldemort.VoldemortException;
import voldemort.store.Store;
import voldemort.store.routed.RoutedStore;
import voldemort.store.socket.SocketStore;
import voldemort.utils.ByteArray;
import voldemort.versioning.Version;
import voldemort.versioning.Versioned;
import java.util.Map;

public interface NonblockingStore {

    public void submitGetRequest(ByteArray key, byte[] transforms, NonblockingStoreCallback callback, long timeoutMs);

    public void submitGetAllRequest(Iterable<ByteArray> keys, Map<ByteArray, byte[]> transforms, NonblockingStoreCallback callback, long timeoutMs);

    public void submitGetVersionsRequest(ByteArray key, NonblockingStoreCallback callback, long timeoutMs);

    public void submitPutRequest(ByteArray key, Versioned<byte[]> value, byte[] transforms, NonblockingStoreCallback callback, long timeoutMs);

    public void submitDeleteRequest(ByteArray key, Version version, NonblockingStoreCallback callback, long timeoutMs);

    public void close() throws VoldemortException;
}
