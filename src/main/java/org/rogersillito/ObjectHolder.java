package org.rogersillito;

public class ObjectHolder<T> {

    private T obj;

    public ObjectHolder(T obj) {
        this.obj = obj;
    }

    public T get() {
        return obj;
    }

    public void set(T obj) {
        this.obj = obj;
    }
}
