package bonken.utils;

@FunctionalInterface
public interface DoubleAction<T, V> {
    void call(T param, V param2);
}
