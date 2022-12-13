package org.rogersillito;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        // using lombok @AllArgsConstructor
        Student student = new Student("Bob", Gender.MALE, Nationality.GERMANY, 35, new Student.Course("Spanish"), new Student.Course("Maths"), new Student.Course("History"), 190.5, 100.0);
        System.out.println(student);

        // sorting
        List<Human> humans = getHumans();

        Comparator<Human> ageComparator = (h1, h2) -> h1.getAge() - h2.getAge();
        humans.sort(ageComparator);
        dump("by age lambda", humans);

        humans = getHumans();
        humans.sort(Human::compareByNameThenAge);
        dump("compareByNameThenAge", humans);

        humans = getHumans();
        humans.sort(Comparator.comparing(Human::getName).reversed());
        dump("comparing(Human::getName).reversed()", humans);

        humans = getHumans();
        humans.sort(Comparator.comparing(Human::getName).reversed().thenComparing(Human::getAge));
        dump("Comparator.comparing(Human::getName).reversed().thenComparing(Human::getAge)", humans);

        var sortedLetters = Stream.of("B", "A", "C").sorted().collect(Collectors.toList());
        dump("stream .sorted() letters", sortedLetters);

        humans = getHumans();
        humans = humans.stream().sorted(ageComparator).collect(Collectors.toList());
        dump("humans.stream().sorted(ageComparator)", humans);

        humans = getHumans();
        humans = humans.stream().sorted(Comparator.comparing(Human::getAge, Comparator.reverseOrder()).thenComparing(Human::getName)).collect(Collectors.toList());
        dump("stream().sorted(Comparator.comparing(Human::getAge, Comparator.reverseOrder()).thenComparing(Human::getName))", humans);

        humans = getHumans();
        humans.add(null);
        humans.set(7, null);
        humans = humans.stream().sorted(Comparator.nullsLast(ageComparator)).collect(Collectors.toList());
        dump("with NULLs: stream().sorted(Comparator.nullsLast(ageComparator))", humans);
    }

    private static List<Human> getHumans() {
        return new ArrayList<>(
                Arrays.asList(
                        new Human("Bob", 50),
                        new Human("Sue", 25),
                        new Human("Ralph", 39),
                        new Human("Ralph", 25),
                        new Human("Barry", 92),
                        new Human("Barry", 83),
                        new Human("Barry", 61),
                        new Human("Barry", 25),
                        new Human("Dwayne", 19),
                        new Human("Dwayne", 39)
                )
        );
    }

    private static <T> void dump(String title, T thing) {
        dump(title, Collections.singletonList(thing));
    }

    private static void dump(String title, List<?> things) {
        System.out.println("----------");
        System.out.println(title);
        System.out.println("----------");
        for (var thing:
                things) {
            System.out.println(thing);
        }
    }
}



