package PlayingAround;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class EstablishingConnection {

    private Connection connection;

    public void connecting () {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcexamples", "tuser", "testing");

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
