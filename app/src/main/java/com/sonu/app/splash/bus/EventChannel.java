package com.sonu.app.splash.bus;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/** Small lifecycle-friendly event channel used by the legacy Java screens. */
public final class EventChannel<T> {

    private final CopyOnWriteArrayList<Consumer<T>> observers = new CopyOnWriteArrayList<>();

    public Subscription subscribe(Consumer<T> observer) {
        observers.add(observer);
        return () -> observers.remove(observer);
    }

    public void emit(T value) {
        for (Consumer<T> observer : observers) {
            observer.accept(value);
        }
    }

    public interface Subscription {
        void cancel();
    }
}
