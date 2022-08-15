import java.sql.*;

public class Examples {

    public static void main(String[] args) {
        /***
         * Tip! Sometimes, it is useful to declare a variable that can be changed later on, especially in a database
         * context. Let's say you have an ID, it can only have positive numbers when stored in the database. So, having
         * a variable of -1 will give you the option later on to create an if (condition == -1) {put into database}.
         * All objects in this context are created with the value of -1, then later on gets sent into the database with
         * a different positive value. So, that if there is an object that hasn't been sent, it will be recognized with
         * value of -1. While the ones retrieved in the database will have a positive number.
         */

        try {
        //connection is a product of DriverManagers.getConnection(). Making that method return the result into connection
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcexamples", "tuser", "testing");
        //Using the variable above to make this variable that gets new properties (the database gets properties?)
        Statement statement = connection.createStatement();

        /* String createTable = "CREATE TABLE IF NOT EXISTS equipmentBall( "
                + "id INT NOT NULL, "
                + "needsAir BOOLEAN, "
                + "location INT NULL) ";

        */

        //String createTable = "DROP TABLE jdbcexamples.equipmentball";

        //executing, but storing in a variable
        //boolean result = statement.execute(createTable);
            /***
             * Before doing anything we would want to create a scenario where things will not commit if everything does
             * not go through. We can state connection.setAutocommit = 0; for example, or:
             * if (condition) {do this}
             * else {connection.commit();}
             * Save points is also very handy, where you can set a point to roll back to, or create checkpoints.
             * example:
             * Savepoint save1 = connection.setSavepoint();
             * -Your code-
             * connection.rollback(save1);
             *
             */
        //Counter SQL injections with prepared statements...
        String sql = "INSERT INTO jdbcexamples.person (name, age)\n" +
                     "VALUES ('Trinity',?)";

        PreparedStatement preparedStatements = connection.prepareStatement(sql);
        preparedStatements.setInt(1, 30);
        preparedStatements.executeUpdate();

        //Statements made to the database returns the results of tables from the database, which needs...
        //Accessing the Statement variable's method and storing it in a variable of the ResultSet class
        ResultSet resultSet = statement.executeQuery("select * from person");//
            /**
             * the below method has to follow the above one, or else the resultSet will close connection before
             * it initializes the next method...
             */
            resultSet.next();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("name"));
        }


        } catch (Exception e) {
            e.printStackTrace();

        }/*
        Might come in handy if connection is lost. But did not include, because Connection connection is not
        in a global scope...
        finally {
             try {
                 if (con!=null) {
                     con.close();
                 }
             }catch (SQLException ex) {
                 ex.printStackTrace();
             }
             }

         */

    }

}
