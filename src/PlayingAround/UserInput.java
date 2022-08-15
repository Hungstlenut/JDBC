package PlayingAround;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInput extends EstablishingConnection{

    private ArrayList<Person> group;
    public UserInput (){
        super();
    }

    public void createPerson () {//TODO handle exceptions
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("How many people are you?");
                Integer amount = sc.nextInt();
                ArrayList<Person> group = new ArrayList<>();

                int i = 0;

                while (i < amount) {
//this loop will run as long as i is less than amount, which means the caught exception allows another input attempt.
                    try {
                        Scanner s = new Scanner(System.in);
                        System.out.println("---------New entry----------");
                        System.out.println("What is your name?");
                        String name = s.nextLine();
                        System.out.println("How old are you?");
                        Integer age = s.nextInt();

                        Person newPerson = new Person(name, age);
                        group.add(newPerson);
                        i++;
                    }catch (Exception e) {
                        System.out.println("Wrong input, try again!");
                    }
                }

                this.group = group;
            }catch (InputMismatchException e){
            System.out.println("Wrong input, try again!");
            createPerson();//runs method again
            }

    }

    public void printGroup(){
        System.out.println("Current group in action: " + group);
    }

    public void putGroupIntoDatabase() {
        try {
            getConnection();
            Connection con = getConnection();
            //Statement stmt = con.createStatement();


            for (int i = 0; i < group.size(); i++) {
                //making a variable from Person that gets created each loop with the index place of i.
                Person p = group.get(i);
                String name = p.getName();
                int age = p.getAge();
                String ability = String.valueOf(p.getAbility());

                String sql = "INSERT INTO `jdbcexamples`.`person`\n" +
                        "(`name`,\n" +
                        "`age`,\n" +
                        "`ability`)\n" +
                        "VALUES\n" +
                        "(?,\n" +
                        "?,\n" +
                        "?);";


                //boolean result = stmt.execute(sql);
                PreparedStatement preparedStmt = con.prepareStatement(sql);
                preparedStmt.setString(1, name);
                preparedStmt.setInt(2, age);
                preparedStmt.setString(3, ability);

                preparedStmt.executeUpdate();
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getAllPeopleFromDatabase () {
        try {
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            String selectSQL = "SELECT * FROM person";

            ResultSet resultSet = stmt.executeQuery(selectSQL);

            //creating a new group
            ArrayList<Person> aGroup = new ArrayList<>();
            this.group = aGroup;

            while (resultSet.next()) {
              //  System.out.println(resultSet.getString("name"));//just to see if things work so far

                    //creating a new person each line
                    Person newPerson = new Person();
                    newPerson.setId(resultSet.getInt("id"));
                    newPerson.setName(resultSet.getString("name"));
                    newPerson.setAge(resultSet.getInt("age"));
                    newPerson.setAbility(Abilities.valueOf(resultSet.getString("ability")));

                    aGroup.add(newPerson);

                }
                System.out.println(aGroup);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }



    public ArrayList<Person> getGroup() {
        return group;
    }

    public void setGroup(ArrayList<Person> group) {
        this.group = group;
    }
}

