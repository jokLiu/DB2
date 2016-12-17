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
		newTables();

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
			PreparedStatement del = conn.prepareStatement("DROP SCHEMA public CASCADE;");
			del.execute();
			
			PreparedStatement setClean = conn.prepareStatement("CREATE SCHEMA public;");
			setClean.execute();
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void newTables()
	{
		createTables();
	}
	
	private void createTables()
	{
		
		try {
			
			
			String childTable   = "CREATE TABLE Child(" +
								  "cid 		INTEGER," +
								  "name 		CHAR(30) 	NOT NULL," +
								  "address 	CHAR(30) 	NOT NULL," +
								  "PRIMARY KEY (cid) " +
								  ");";
			
			String helperTable = "CREATE TABLE SantasLittleHelper(" +
					 			  "slhid	INTEGER," +
					 			  "name 	CHAR(30) NOT NULL," +
					 			  "PRIMARY KEY (slhid)" +
					 			  ");";
			
			String giftTable   = "CREATE TABLE Gift(" +
								  "gid		INTEGER," +
								  "DESCRIPTION TEXT," +
								  "PRIMARY KEY (gid)" +
								  ");";
			
			String presentTable = "CREATE TABLE Present(" +
								  "gid		INTEGER," +
								  "cid 		INTEGER," +
								  "slhid	INTEGER," +
								  
								  "FOREIGN KEY (gid) REFERENCES Gift(gid) " +
								  	"ON DELETE CASCADE " +
								  	"ON UPDATE CASCADE," +
								  
								  "FOREIGN KEY (cid) REFERENCES Child(cid) " +
								  	"ON DELETE RESTRICT " +// On delete cascade
								  	"ON UPDATE CASCADE, " +
								  									  
								  "FOREIGN KEY (slhid) REFERENCES SantasLittleHelper(slhid) " +
								  	"ON DELETE CASCADE " +
								  	"ON UPDATE CASCADE " +
		
								  ");";
			
			 PreparedStatement createChild = conn.prepareStatement(childTable);
			 PreparedStatement createHelper = conn.prepareStatement(helperTable);
			 PreparedStatement createGift = conn.prepareStatement(giftTable);
			 PreparedStatement createPresent = conn.prepareStatement(presentTable);
			 
			 createChild.executeUpdate();
			 createHelper.executeUpdate();
			 createGift.executeUpdate();
			 createPresent.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
