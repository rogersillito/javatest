package org.rogersillito;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Human implements Hominidae {
    @Getter
    private String name;
    @Getter
    private int age;

    public static int compareByNameThenAge(Human lhs, Human rhs) {
        if (lhs.name.equals(rhs.name)) {
            return Integer.compare(lhs.age, rhs.age);
        } else {
            return lhs.name.compareTo(rhs.name);
        }
    }
}
