package bonken.utils;

@FunctionalInterface
public interface Action<T> {
    void call(T param);
}
