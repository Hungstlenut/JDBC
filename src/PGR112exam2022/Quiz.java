package PGR112exam2022;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public abstract class Quiz {

    Integer quizId;
    String question;
    private Integer correctAnswer;
    private Connection connection;

    private ArrayList<User> PlayerList;

    //Establishing a connection to my database
    public void connecting () {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizdb", "tuser", "testing");

        }catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void newGame () {//Initiates everything
        createScoreTable();
        Scanner userInput = new Scanner(System.in);

        System.out.println("I have 2 games you can play about Martial art!\n[1] Binary quiz\n[2] Multichoice quiz.");
        String choice = userInput.nextLine();

        if (Objects.equals(choice, "1")) {
            BinaryQuiz newBinaryEntry = new BinaryQuiz();
            newBinaryEntry.initBinaryQuiz();
        }else if (Objects.equals(choice, "2")) {
            MultichoiceQuiz newMultiEntry = new MultichoiceQuiz();
            newMultiEntry.initMultichoiceQuiz();
        }

    }

    public void createScoreTable () {
        try {
            connecting();
            String sql = "CREATE TABLE IF NOT EXISTS `quizdb`.`score` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `user` VARCHAR(45) NULL,\n" +
                    "  `score` VARCHAR(45) NULL,\n" +
                    "  `topic` VARCHAR(45) NULL,\n" +
                    "  PRIMARY KEY (`id`));";

            Statement stmt = getConnection().createStatement();
            stmt.executeUpdate(sql);

        }catch (Exception e) {
            e.printStackTrace();
        }


    }

//    public void searchInScoreTable () {
//        Scanner userInput = new Scanner(System.in);
//        System.out.println("What is the name you're searching for?");
//        String name = userInput.nextLine();
//
//        try {
//            connecting();
//            String sql =    "SELECT * FROM quizdb.score\n" +
//                            "WHERE user LIKE '%'?'%';";
//
//            PreparedStatement pStmt = connection.prepareStatement(sql);
//            pStmt.setString(1, name);
//            pStmt.executeQuery();
//
//
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void showHighscore () {
        try {

            connecting();
            String sql = "SELECT * FROM quizdb.score";
            Statement stmt = this.connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            System.out.println("--- Max score is 3 ---");

            while (resultSet.next()) {
                UserScore us = new UserScore();
                us.setName(resultSet.getString("user"));
                us.setScore(resultSet.getInt("score"));
                us.setTopic(Topic.valueOf(resultSet.getString("topic")));

                System.out.println("Name: " + us.getName() + ", Score: " + us.getScore() + ", Topic: " + us.getTopic());
            }


        }catch (Exception e) {
            e.printStackTrace();
        }

    }


    //creating a method that makes a random number
    public static Integer randomInteger (Integer maximum) {
        //using Math random gives me a number between 0 and 1, which is why I am multiplying with maximum
        return ((int)(Math.random()* maximum));
    }

    public Integer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Integer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<User> getPlayerList() {
        return PlayerList;
    }

    public void setPlayerList(ArrayList<User> playerList) {
        PlayerList = playerList;
    }




}
