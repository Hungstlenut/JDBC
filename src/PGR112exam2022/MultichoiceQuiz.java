package PGR112exam2022;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

//I used the same steps with some small modifications from BinaryQuiz class
public class MultichoiceQuiz extends Quiz{

    String answerA;
    String answerB;
    String answerC;
    String answerD;

    ArrayList<MultichoiceQuiz> multichoiceQuizList;
    public void createMultichoiceTable () {
        try {
            connecting();
            String sql1 = "CREATE TABLE IF NOT EXISTS `quizdb`.`multichoicequiz` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `question` VARCHAR(200) NULL,\n" +
                    "  `answera` VARCHAR(200) NULL,\n" +
                    "  `answerb` VARCHAR(200) NULL,\n" +
                    "  `answerc` VARCHAR(200) NULL,\n" +
                    "  `answerd` VARCHAR(200) NULL,\n" +
                    "  `correctanswer` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id`));";


            Statement stmt = getConnection().createStatement();
            stmt.executeUpdate(sql1);

            //somehow I could not make the insert statements work all together. So I did them individually...

            String sql2 = "INSERT INTO quizdb.multichoicequiz (question, answera, answerb, answerc, answerd, correctanswer)\n" +
                    "VALUES ('How many rounds are there in an UFC main event?', '[1] 3', '[2] 4', '[3] 5', '[4] 6', 3);";

            stmt.executeUpdate(sql2);

            String sql3 = "INSERT INTO quizdb.multichoicequiz (question, answera, answerb, answerc, answerd, correctanswer)\n" +
                    "VALUES ('Who is the world champion in boxing today (year 2022)?', '[1] Tyson Fury', '[2] Deontay Wilder', '[3] Mike Tyson', '[4] Anthony Joshua', 1);";

            stmt.executeUpdate(sql3);

            String sql4 = "INSERT INTO quizdb.multichoicequiz (question, answera, answerb, answerc, answerd, correctanswer)\n" +
                    "VALUES ('What is body hardening?', '[1] The art of a strong mind', '[2] Having low body fat percentage', '[3] The art of increasing bone mineral density', '[4] Breathing exercises', 3);";

            stmt.executeUpdate(sql4);

            String sql5 = "INSERT INTO quizdb.multichoicequiz (question, answera, answerb, answerc, answerd, correctanswer)\n" +
                    "VALUES ('Who is considered the father of MMA?', '[1] Vladimir Putin', '[2] Muhammed Ali', '[3] Chuck Norris', '[4] Bruce Lee', 4);";

            stmt.executeUpdate(sql5);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void pullingMultiChoiceQuestionsFromDatabase() {
        connecting();

        try {

            String sql = "SELECT * FROM quizdb.multichoicequiz;";
            Statement stmt = getConnection().createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            ArrayList<MultichoiceQuiz> newList = new ArrayList<>();
            multichoiceQuizList = newList;

            while (resultSet.next()) {

                MultichoiceQuiz mc = new MultichoiceQuiz();

                mc.setQuizId(resultSet.getInt("id"));
                mc.setQuestion(resultSet.getString("question"));
                mc.answerA = resultSet.getString("answera");
                mc.answerB = resultSet.getString("answerb");
                mc.answerC = resultSet.getString("answerc");
                mc.answerD = resultSet.getString("answerd");
                mc.setCorrectAnswer(resultSet.getInt("correctanswer"));

                newList.add(mc);
            }
            //System.out.println(multichoiceQuizList); //Used to see if the scanner got it everything.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initMultichoiceQuiz () {
        createMultichoiceTable();
        pullingMultiChoiceQuestionsFromDatabase();

        Scanner userInput = new Scanner(System.in);
        System.out.println("What is your name?");
        String name = userInput.nextLine();

        UserScore newEntry = new UserScore(name);

        newEntry.setTopic(Topic.MULTI);


        for (int i = 0; i <= 4; i++) {
            Integer randomNumber = randomInteger(3);
            System.out.println(multichoiceQuizList.get(randomNumber).getQuestion());
            switch (randomNumber) {
                case 0 -> {
                    System.out.println("[1] 3 rounds, [2] 4 rounds, [3] 5 rounds, [4] 6 rounds");
                }
                case 1 -> {
                    System.out.println("[1] Tyson Fury, [2] Deontay Wilder, [3] Mike Tyson, [4] Anthony Joshua");
                }
                case 2 -> {
                    System.out.println("[1] The art of a strong mind, [2] Having low body fat percentage, [3] The art of increasing bone mineral density, [4] Breathing exercises");
                }
                case 3 -> {
                    System.out.println("[1] Vladimir Putin, [2] Muhammed Ali, [3] 5 Chuck Norris, [4] Bruce Lee");
                }
            }
            System.out.println("What is you answer?");
            int answer = userInput.nextInt();

            if (randomNumber == 0) {
                //System.out.println("[1] 3 rounds, [2] 4 rounds, [3] 5 rounds, [4] 6 rounds");
                if (answer == 3) {
                    System.out.println("Correct!");
                    newEntry.incrementScore();
                }else {
                    System.out.println("Incorrect!");
                }
            }
            if (randomNumber == 1) {
                //System.out.println("[1] Tyson Fury, [2] Deontay Wilder, [3] Mike Tyson, [4] Anthony Joshua");
                if (answer == 1) {
                    System.out.println("Correct!");
                    newEntry.incrementScore();
                }else {
                    System.out.println("Incorrect!");
                }
            }
            if (randomNumber == 2) {
                //System.out.println("[1] The art of a strong mind, [2] Having low body fat percentage, [3] The art of increasing bone mineral density, [4] Breathing exercises");
                if (answer == 3) {
                    System.out.println("Correct!");
                    newEntry.incrementScore();
                }else {
                    System.out.println("Incorrect!");
                }
            }
            if (randomNumber == 3) {
                //System.out.println("[1] Vladimir Putin, [2] Muhammed Ali, [3] 5 Chuck Norris, [4] Bruce Lee");
                if (answer == 4) {
                    System.out.println("Correct!");
                    newEntry.incrementScore();
                }else {
                    System.out.println("Incorrect!");
                }
            }
            i++;
        }
        try {
            String sql = "INSERT INTO quizdb.score (user, score, topic)\n" +
                    "VALUES (?, '" + newEntry.getScore() + "', '" + newEntry.getTopic() + "');";

            PreparedStatement pStmt = getConnection().prepareStatement(sql);
            pStmt.setString(1, newEntry.getName());
            pStmt.executeUpdate();


        }catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("[1] Play another game\n[2] Finish and show highscore");
        int anotherTry = userInput.nextInt();
        if (anotherTry == 1) {
            newGame();
        }else {
            showHighscore();
        }
    }

    public String toString() {
        return "ID: " + getQuizId() + ", Question: " + getQuestion() + ", Answer A: " + this.answerA + ", Answer B: " + this.answerB + ", Answer C: " + this.answerC + ", Answer D: " + this.answerD + ", Correct answer: " + getCorrectAnswer();
    }

}
