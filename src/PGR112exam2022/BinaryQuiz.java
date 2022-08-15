package PGR112exam2022;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class BinaryQuiz extends Quiz {

    ArrayList<Quiz> binaryQuizList;


    @Override
    public void connecting() {
        super.connecting();

    }

    public void createBinaryTable () {
        try {
            connecting();
            String sql1 = "CREATE TABLE IF NOT EXISTS `quizdb`.`binaryquiz` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `question` VARCHAR(45) NULL,\n" +
                    "  `correctanswer` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id`));";


            Statement stmt = getConnection().createStatement();
            stmt.executeUpdate(sql1);

            //somehow I could not make the insert statements work all together. So I did them individually...

            String sql2 = "INSERT INTO quizdb.binaryquiz (question, correctanswer)\n" +
                    "VALUES ('Is martial art lethal?', 1);";

            stmt.executeUpdate(sql2);

            String sql3 = "INSERT INTO quizdb.binaryquiz (question, correctanswer)\n" +
                    "VALUES ('Does muay thai include elbows?', 1);";

            stmt.executeUpdate(sql3);

            String sql4 = "INSERT INTO quizdb.binaryquiz (question, correctanswer)\n" +
                    "VALUES ('Is MMA a martial art?', 2);";

            stmt.executeUpdate(sql4);

            String sql5 = "INSERT INTO quizdb.binaryquiz (question, correctanswer)\n" +
                    "VALUES ('Is Tae Kwon Do a japanese dicipline?', 2);";

            stmt.executeUpdate(sql5);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void pullingBinaryQuestionsFromDatabase() {
        connecting();

        try {

            String sql = "SELECT * FROM quizdb.binaryquiz;";
            Statement stmt = getConnection().createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            //creating a new list to hold all the questions pulled from the database
            ArrayList<Quiz> newList = new ArrayList<>();
            binaryQuizList = newList;

            while (resultSet.next()) {
                //creating a new instance of each question
                BinaryQuiz bq = new BinaryQuiz();

                bq.setQuizId(resultSet.getInt("id"));
                bq.setQuestion(resultSet.getString("question"));
                bq.setCorrectAnswer(resultSet.getInt("correctanswer"));
                //adding this to the list
                newList.add(bq);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void initBinaryQuiz () {
        createBinaryTable();
        pullingBinaryQuestionsFromDatabase();
        //Using a scanner to pick up users name
        Scanner userInput = new Scanner(System.in);
        System.out.println("What is your name?");
        String name = userInput.nextLine();
        //Making a new entry
        UserScore newEntry = new UserScore(name);
        //Setting the topic to this entry
        newEntry.setTopic(Topic.BINARY);


        for (int i = 0; i <= 4; i++) {
            Integer randomNumber = randomInteger(3); //because I have 4 questions in this category, but 0 counts in this case.
            System.out.println(binaryQuizList.get(randomNumber).getQuestion());

            System.out.println("What is you answer? (1 = Yes, 2 = No)");
            int answer = userInput.nextInt();

            if (randomNumber == 0) {
                if (answer == 1) {
                    System.out.println("Correct!");
                    newEntry.incrementScore();
                }else {
                    System.out.println("Incorrect!");
                }
            }
            if (randomNumber == 1) {
                if (answer == 1) {
                    System.out.println("Correct!");
                    newEntry.incrementScore();
                }else {
                    System.out.println("Incorrect!");
                }
            }
            if (randomNumber == 2) {
                if (answer == 2) {
                    System.out.println("Correct!");
                    newEntry.incrementScore();
                }else {
                    System.out.println("Incorrect!");
                }
            }
            if (randomNumber == 3) {
                if (answer == 2) {
                    System.out.println("Correct!");
                    newEntry.incrementScore();
                }else {
                    System.out.println("Incorrect!");
                }
            }
            i++;
        }
        try {
        //Doing a prepared statement here, so that I protect the database against SQL injections when they type in their name.
            String sql = "INSERT INTO quizdb.score (user, score, topic)\n" +
                    "VALUES (?, '" + newEntry.getScore() + "', '" + newEntry.getTopic() + "');";

            PreparedStatement pStmt = getConnection().prepareStatement(sql);
            pStmt.setString(1, newEntry.getName());
            pStmt.executeUpdate();


        }catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("[1] Play another game\n[2] Finish and how highscore");
        int anotherTry = userInput.nextInt();
        if (anotherTry == 1) {
            newGame();
        }else {
            showHighscore();
        }
    }



    public String toString() {
        return "ID: " + getQuizId() + ", Question: " + getQuestion() + ", Correct answer: " + getCorrectAnswer();
    }
}

