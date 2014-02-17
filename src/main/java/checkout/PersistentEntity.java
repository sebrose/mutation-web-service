package checkout;


public interface PersistentEntity<K, V> {
    void update(K id, V value);
    V get(K id);
}
