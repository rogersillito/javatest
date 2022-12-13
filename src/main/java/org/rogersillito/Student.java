package org.rogersillito;

//import lombok.*;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.NonNull;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@ToString
public class Student {
    @NonNull
    private String name;
    private final Gender gender;
    private final Nationality nationality;
    @NonNull
    private Integer age;
    @Getter
    @Setter
    private Course course1;
    @Getter
    @Setter
    private Course course2;
    @Getter
    @Setter
    private Course course3;
    @Getter
    @Setter
    private Double height;
    @Getter
    @Setter
    @ToString.Include(rank=1)
    private Double weight;

    @AllArgsConstructor
    @ToString
    public static class Course {
        @Getter
        private String name;
    }
}
