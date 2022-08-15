package PGR112exam2022;

import java.util.ArrayList;

public abstract class User {

        private String name;

        public User () {

        }
        public User (String name) {
            this.name = name;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String toString () {
            return "Name: " + name;
        }


}


