package org.rogersillito;

public interface Chordate extends Animal {
    default String getPhylum() {
        return "Chordata";
    }
}
