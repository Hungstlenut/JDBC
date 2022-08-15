package PlayingAround;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Person implements Human{
    private Integer id;
    private String name;
    private Integer age;

    private Abilities ability;

    public Person (String name, Integer age) {

        if (Objects.equals(name, "Neo")) {
            this.ability = Abilities.THEONE;
        }
        else if (Objects.equals(name, "Morpheus")) {
            this.ability = Abilities.THECAPTAIN;
        }
        else if (Objects.equals(name, "Trinity")) {
            this.ability = Abilities.THEOTHERONE;
        }
        else {this.ability = Abilities.NORMIE;}

        this.name = name;
        this.age = age;
        this.id = ID;//from Human interface
    }

    public Person() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Abilities getAbility() {
        return ability;
    }

    public void setAbility(Abilities ability) {
        this.ability = ability;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String toString() {
        return "ID: "+ getId() +" Name: " + getName() + ", age: " + getAge() + ", \nability: " + getAbility().ability + "\n";
    }
}
