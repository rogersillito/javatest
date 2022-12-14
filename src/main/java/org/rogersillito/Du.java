package org.rogersillito;

import java.util.Collections;
import java.util.List;

public class Du {
    public static <T> void mp(String title, T thing) {
        mp(title, Collections.singletonList(thing));
    }

    public static void mp(String title, List<?> things) {
        System.out.println("----------");
        System.out.println(title);
        System.out.println("----------");
        for (var thing :
                things) {
            System.out.println(thing);
        }
    }
}
