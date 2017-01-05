package database.createAndPopulate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

	private Connection conn;
	private String name;
	private String psw;

	public Connect(String name, String psw) {
		this.name = name;
		this.psw = psw;
	}

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	public Connection getConnection() {
		registerDriver();
		connectServer();
		return conn;
	}

	/**
	 * Close connection.
	 */
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.err.println("Failed to close the connection");
		}
	}

	/**
	 * Register postgreSQL driver.
	 */
	private void registerDriver() {
		System.out.println("Setting up database.");
		System.out.println("Register postgreSQL driver");
		try {

			// Load the PostgreSQL JDBC driver
			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException ex) {

			// If driver not found exit
			System.out.println("Driver not found");
			System.exit(1);
		}

		System.out.println("PostgreSQL driver registered.");
	}

	/**
	 * Connect to database server.
	 */
	private void connectServer() {
		System.out.println("Connecting database");
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mydb", "postgres", "258453Ak");
			// conn =
			// DriverManager.getConnection("jdbc:postgresql://mod-intro-databases/jxl706",
			// name, psw);
		} catch (SQLException e) {
			System.out.println("Ooops, couldn't get a connection");
			System.out.println("Check that <username> & <password> are right");
			System.exit(1);
		}

		System.out.println(conn != null ? "Database accessed!" : "Failed to make connection");
		if (conn == null)
			System.exit(1);
	}
}