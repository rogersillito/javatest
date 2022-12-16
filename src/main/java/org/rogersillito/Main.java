package org.rogersillito;

import org.rogersillito.medialib.DirWalker;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        try {
            DirWalker.walk("C:\\DriveD\\_TEMP_GD\\Music Downloads");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        testLombokAllArgsConstructor();
//        testComparators();
//        testNestedClasses();
//        testInterfaceDefaults();
//        testStreams();
    }

    private static void testStreams() {
        List<Integer> integerList = IntStream.rangeClosed(1, 10).boxed().toList();

        int total = 0;
        Du.mp("reduce", integerList.stream().reduce(total, (i1, i2) -> i1 + i2 + 1));

        var oh = new ObjectHolder<>(0);
        integerList.stream().parallel().forEach(el -> {
            Du.mp(oh.get() + ": ", el);
            oh.set(oh.get() + 1);
        });

        Du.mp("anyMatch 3", integerList.stream().anyMatch(i -> {
            Du.mp("anyMatch predicate", i);
            return i == 3;
        }));

        Du.mp("filter % 3 = 0", integerList.stream().filter(i -> {
            Du.mp("filter predicate", i);
            return (i % 3) == 0;
        }).toList());

        Du.mp("map char", integerList.stream().map(Character::getName).toList());

        List<Integer> someList = Arrays.asList(1, 2, 3);
        //TODO: collect as mapping - fully understand intellisense on this...
//        someList.stream().collect(Collectors.mapping(integer -> ))
    }

    private static void testInterfaceDefaults() {
        class SuperTester {
            public static List<? super Cat> randomFilter(List<? super Cat> catSuperclassList) {
                var catSuperclassInstance = catSuperclassList.stream().findAny();
                if (catSuperclassInstance.isPresent()) {
                    var e1 = catSuperclassInstance.get();
                    //TODO: figure out how to break super!!
                    Du.mp("filtering", e1);

                    return List.of(e1);
                }
                return List.of();
            }
        }
//        Cat cat = new Cat();
//        cat.eat();
//        Du.mp("Kingdom = ", cat.getKingdom());
//        Du.mp("Phylum = ", cat.getPhylum());
//        Du.mp("Family = ", cat.getFamily());
//        ((Animal) cat).eat();
//        cat.miaow();
//
        Human human = new Human("Phil", 20);
//        human.eat();
//        Du.mp("Kingdom = ", human.getKingdom());
//        Du.mp("Phylum = ", human.getPhylum());
//        Du.mp("Family = ", human.getFamily());
//        human.eat();
//        human.stand();

        var filtered = SuperTester.randomFilter(List.of(human));
//        Du.mp("filtered list with human", filtered);

        // anonymous Hominidae implementation
        var felidae = new Felidae() { };
        var someAnimal = new Animal() { };
        isSuperclass(human, felidae);
        isSuperclass(someAnimal, felidae);


        var filtered2 = SuperTester.randomFilter(List.of(felidae));
//        Du.mp("filtered list with hominid", filtered2);
    }

    private static boolean isSuperclass(Object superClass, Object derivedClass) {
        var sClass = superClass.getClass();
        var dClass = derivedClass.getClass();
        var isAssignableFrom = sClass.isAssignableFrom(dClass);
        Du.mp(String.format("%s isSuperclass of %s", sClass, dClass), isAssignableFrom);
        return isAssignableFrom;
    }

    private static void testNestedClasses() {
        var at = new NestedClassesTest();
        at.setMultiplier(1);
        at.doTestHarness();
        at.setMultiplier(3);
        at.doTestHarness();
    }

    private static void testLombokAllArgsConstructor() {
        Student student = new Student("Bob", Gender.MALE, Nationality.GERMANY, 35, new Student.Course("Spanish"), new Student.Course("Maths"), new Student.Course("History"), 190.5, 100.0);
        Du.mp("using lombok @AllArgsConstructor", student);
    }

    private static void testComparators() {
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



