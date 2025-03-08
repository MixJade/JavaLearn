package entiy;

import lombok.Getter;

@Getter
public class User {
    String name;
    int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}