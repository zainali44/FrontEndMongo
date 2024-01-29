package DatabaseHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    String url = "jdbc:mysql://localhost:3306/online_shopping_system";
    String username = "root";
    String password = "oracle4me";
    public Connection connection;
    public Statement statement;


    public Database() throws SQLException {
        System.out.println("CONNECTED");
        connection = DriverManager.getConnection(url,username,password);
        statement = connection.createStatement();
    }
}
