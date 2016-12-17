import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateAndPopulate {

	private BufferedReader reader;
	private String name = "jxl706";
	private String psw = "jvrhx1oucf";
	private Connection conn;

	public CreateAndPopulate(Connection conn) {
		// initializeRaeder();
		// readNamePsw();
		this.conn = conn;
		registerDriver();
		connectServer();
		deleteTables();

	}

	private void readNamePsw() {
		String[] arg;
		try {
			arg = reader.readLine().split(" ");
			name = arg[0];
			psw = arg[1];

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("failed to read from the file");
		}

	}

	private void initializeRaeder() {
		try {
			File file = new File("db-password.txt");
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File not found!");
		}
	}

	private void registerDriver() {
		try {

			// Load the PostgreSQL JDBC driver
			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException ex) {
			System.out.println("Driver not found");
			System.exit(1);
		}

		System.out.println("PostgreSQL driver registered.");
	}

	private void connectServer() {
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://mod-intro-databases/jxl706", name, psw);
		} catch (SQLException e) {
			System.out.println("Ooops, couldn't get a connection");
			System.out.println("Check that <username> & <password> are right");
			System.exit(1);
		}

		System.out.println(conn != null ? "Database accessed!" : "Failed to make connection");
		if (conn == null)
			System.exit(1);
	}

	private void deleteTables() {
		System.out.println("Deleting all the tables");

		try {
			PreparedStatement del = conn.prepareStatement("DROP SCHEMA Child CASCADE;");
			//del.setString(1, "Child");
			del.executeQuery();
			/*
			 * del.setString(1, "SantasLittleHelper"); del.executeQuery();
			 * del.setString(1, "Gift"); del.executeQuery(); del.setString(1,
			 * "Present"); del.executeQuery();
			 */
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
