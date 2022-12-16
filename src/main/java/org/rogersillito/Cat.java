package org.rogersillito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cat implements Felidae {
    private List<Animal> friends = new ArrayList<>();
    @Override
    public void eat() {
        Du.mp("Cat is", "eating");
    }

    public void befriend(Animal otherAnimal) {
        friends.add(otherAnimal);
        Du.mp("Cat has befriended", otherAnimal);
    }

    public List<Animal> getFriends() {
        return Collections.unmodifiableList(friends);
    }
}
