package org.rogersillito;


public interface Felidae extends Chordate {
    default String getFamily() {
        return "Felidae";
    }

    default void miaow() {
        Du.mp("Speaking", "miaow");
    }
}
