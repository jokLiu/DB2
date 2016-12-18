import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect{
	
	private Connection conn;
	private String name;
	private String psw;
	
	public Connect(	Connection conn, String name, String psw)
	{
		this.conn = conn;
		this.name = name;
		this.psw = psw;
	}
	
	public Connection getConnection()
	{
		registerDriver();
		connectServer();
		return conn;
	}
	
	/**
	 * Register postgreSQL driver.
	 */
	private void registerDriver() {
		System.out.println("Setting up database.");
		System.out.println("0/5  Register postgreSQL driver");
		try {

			// Load the PostgreSQL JDBC driver
			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException ex) {

			// If driver not found exit
			System.out.println("Driver not found");
			System.exit(1);
		}

		System.out.println("1/5  PostgreSQL driver registered.");
	}

	/**
	 * Connect to database server.
	 */
	private void connectServer() {
		System.out.println("1/5  Connecting database");
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://mod-intro-databases/jxl706", name, psw);
		} catch (SQLException e) {
			System.out.println("Ooops, couldn't get a connection");
			System.out.println("Check that <username> & <password> are right");
			System.exit(1);
		}

		System.out.println(conn != null ? "2/5  Database accessed!" : "Failed to make connection");
		if (conn == null)
			System.exit(1);
	}
}