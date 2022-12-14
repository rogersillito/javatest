package org.rogersillito;

public interface Animal {
    static String getKingdom() {
        return getName() + "ia";
    }

    private static String getName() {
        return "Animal";
    }

    default void eat() {
        Du.mp("Eating", "yum");
    }
}
