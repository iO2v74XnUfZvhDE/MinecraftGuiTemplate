package minecraftguitemplate.me.config.values;

public class Value <T> {
    private T value;
    private final String name;

    public Value(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public Value<T> set(T value) {
        this.value = value;
        return this;
    }

    public T get() {
        return value;
    }

    public String getName() {
        return name;
    }
}
