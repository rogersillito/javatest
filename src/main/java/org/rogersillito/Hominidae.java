package org.rogersillito;

public interface Hominidae extends Chordate {
    default String getFamily() {
        return "Hominidae";
    }

    default void stand() {
        Du.mp("Stand", "yep, I'm standing");
    }
}
