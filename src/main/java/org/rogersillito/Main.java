package org.rogersillito;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
//        TestLombokAllArgsConstructor();
//        TestComparators();
//        TestNestedClasses();

        Du.mp("Kingdom = ", Animal.getKingdom());
        Cat cat = new Cat();
        cat.eat();
        ((Animal)cat).eat();
    }

    private static void TestNestedClasses() {
        var at = new NestedClassesTest();
        at.setMultiplier(1);
        at.doTestHarness();
        at.setMultiplier(3);
        at.doTestHarness();
    }

    private static void TestLombokAllArgsConstructor() {
        Student student = new Student("Bob", Gender.MALE, Nationality.GERMANY, 35, new Student.Course("Spanish"), new Student.Course("Maths"), new Student.Course("History"), 190.5, 100.0);
        Du.mp("using lombok @AllArgsConstructor", student);
    }

    private static void TestComparators() {
        List<Human> humans = getHumans();

        Comparator<Human> ageComparator = (h1, h2) -> h1.getAge() - h2.getAge();
        humans.sort(ageComparator);
        Du.mp("by age lambda", humans);

        humans = getHumans();
        humans.sort(Human::compareByNameThenAge);
        Du.mp("compareByNameThenAge", humans);

        humans = getHumans();
        humans.sort(Comparator.comparing(Human::getName).reversed());
        Du.mp("comparing(Human::getName).reversed()", humans);

        humans = getHumans();
        humans.sort(Comparator.comparing(Human::getName).reversed().thenComparing(Human::getAge));
        Du.mp("Comparator.comparing(Human::getName).reversed().thenComparing(Human::getAge)", humans);

        var sortedLetters = Stream.of("B", "A", "C").sorted().collect(Collectors.toList());
        Du.mp("stream .sorted() letters", sortedLetters);

        humans = getHumans();
        humans = humans.stream().sorted(ageComparator).collect(Collectors.toList());
        Du.mp("humans.stream().sorted(ageComparator)", humans);

        humans = getHumans();
        humans = humans.stream().sorted(Comparator.comparing(Human::getAge, Comparator.reverseOrder()).thenComparing(Human::getName)).collect(Collectors.toList());
        Du.mp("stream().sorted(Comparator.comparing(Human::getAge, Comparator.reverseOrder()).thenComparing(Human::getName))", humans);

        humans = getHumans();
        humans.add(null);
        humans.set(7, null);
        humans = humans.stream().sorted(Comparator.nullsLast(ageComparator)).collect(Collectors.toList());
        Du.mp("with NULLs: stream().sorted(Comparator.nullsLast(ageComparator))", humans);
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
}



