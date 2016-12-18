import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
		populate();
		test();
		
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
								  "description TEXT," +
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

	private void populate()
	{
		
		
		try {
			String child = "INSERT INTO Child (cid, name, address) " +
					   "VALUES (? , ?, ?); ";
			
			PreparedStatement singleChild = conn.prepareStatement(child);
			for(int i=1; i<=1000; i++)
			{
				singleChild.setInt(1, i);
				singleChild.setString(2, ("John "+i));
				singleChild.setString(3, i + " Birstol Road" );
				singleChild.executeUpdate();
			}
			
			
			String helper = "INSERT INTO SantasLittleHelper (slhid, name) " +
					   "VALUES (? , ?); ";
			
			PreparedStatement singleHelper = conn.prepareStatement(helper);
			for(int i=1; i<=100; i++)
			{
				singleHelper.setInt(1, i);
				singleHelper.setString(2, ("Snowy "+i));
				singleHelper.executeUpdate();
			}
			
			String gift = "INSERT INTO Gift (gid, description) " +
					   "VALUES (? , ?); ";
			
			PreparedStatement singleGift = conn.prepareStatement(gift);
			for(int i=1; i<=100; i++)
			{
				singleGift.setInt(1, i);
				singleGift.setString(2, ("Soft and fluffy bear with sound " + i));
				singleGift.executeUpdate();
			}
			
			
			String present = "INSERT INTO Present (gid, cid, slhid) " +
			         "VALUES (? , ?, ?); ";
	
			PreparedStatement singlePresent = conn.prepareStatement(present);
			for(int i=1; i<=100; i++)
			{
				singlePresent.setInt(1, i);
				singlePresent.setInt(2, i);
				singlePresent.setInt(3, i);
				singlePresent.executeUpdate();
				
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//PreparedStatement chil= conn
	}

	
	
	private void test()
	{
		 try {
	        	PreparedStatement studentQuery = conn.prepareStatement(
	        		"SELECT * FROM Child");
	        
	        
	        	ResultSet rs = studentQuery.executeQuery();
	        
	        	String title = null;
	        
	        	// Now interate through the books just picking up the title
	        	while (rs.next()) {
	        		title= rs.getString("name");
	        		System.out.println(title);
	        	}
	        } catch (SQLException sqlE)
	        { System.out.println("SQL code is broken");
	        
	        }
	        
	        //Now, just tidy up by closing connection
	        try {
	            conn.close();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	}
}
