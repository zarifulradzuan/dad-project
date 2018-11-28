package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	String driver;
	String connectionURL;
	String dbName;
	String username;
	String password;
	public DatabaseConnection() {
		driver = "com.mysql.jdbc.Driver";
		connectionURL = "jdbc:mysql://localhost/";
		dbName = "financial";
		username = "root";
		password = "";
	}
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(connectionURL+dbName+"?useSSL=false", username, password);
		return connection;
		
	}
}