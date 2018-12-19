package com.spacelife.state;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventHandler<T> {
    private final List<Consumer<T>> listeners = new ArrayList<>();

    public void addListener(Consumer<T> listener) {
        listeners.add(listener);
    }

    public void removeListener(Consumer<T> listener) {
        listeners.remove(listener);
    }

    public void invoke(T t) {
        for (Consumer<T> listener : listeners) {
            listener.accept(t);
        }
    }
}
