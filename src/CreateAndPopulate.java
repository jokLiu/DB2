import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The Class CreateAndPopulate.
 */
public class CreateAndPopulate {


	/** The connection */
	private Connection conn;

	/**
	 * Instantiates a new creates the and populate.
	 *
	 * @param conn
	 *            the connection
	 * @param name
	 *            the name of user
	 * @param psw
	 *            the password of user
	 */
	public CreateAndPopulate(Connection conn) {

		this.conn = conn;
		
		deleteTables();
		newTables();
		conn = this.conn;

	}


	/**
	 * Delete previous tables from the database (clean database)
	 */
	private void deleteTables() {
		System.out.println("2/5  Deleting all the tables");

		try {
			PreparedStatement del = conn.prepareStatement("DROP SCHEMA public CASCADE;");
			del.execute();

			PreparedStatement setClean = conn.prepareStatement("CREATE SCHEMA public;");
			setClean.execute();

		} catch (SQLException e) {
			System.out.println("Failed to delete tables ");
			System.exit(1);
		}
		System.out.println("3/5  Successfully cleaned the tables");
	}

	/**
	 * Creating new tables and populating with the new data
	 */
	private void newTables() {
		createTables();
		populate();

	}

	/**
	 * Creates new tables.
	 */
	private void createTables() {
		System.out.println("3/5  Creating new tables");
		try {

			PreparedStatement createChild = conn.prepareStatement(  "CREATE TABLE Child("
																  + "cid 		INTEGER," 
																  + "name 		TEXT 		NOT NULL,"
																  + "address 	CHAR(30) 	NOT NULL," 
																  + "PRIMARY KEY (cid) " 
																  + ");");
			
			
			
			
			PreparedStatement createHelper = conn.prepareStatement( "CREATE TABLE SantasLittleHelper(" 
																  + "slhid	INTEGER," 
																  + "name 	TEXT 	NOT NULL,"
																  + "PRIMARY KEY (slhid)" 
																  + ");");
			
						
			PreparedStatement createGift = conn.prepareStatement(   "CREATE TABLE Gift(" 
															      + "gid		INTEGER," 
															      + "description TEXT," 
															      + "PRIMARY KEY (gid)"
															      + " );" ); 
					
			PreparedStatement createPresent = conn.prepareStatement("CREATE TABLE Present(" 
																  + "gid		INTEGER," 
																  + "cid 		INTEGER,"
																  + "slhid	    INTEGER," 
																  + "FOREIGN KEY (gid) REFERENCES Gift(gid) " 
																  + "ON DELETE CASCADE " 
																  + "ON UPDATE CASCADE," 
																  +	"FOREIGN KEY (cid) REFERENCES Child(cid) " 
																  + "ON DELETE RESTRICT " 
																  + "ON UPDATE CASCADE, " 
																  + "FOREIGN KEY (slhid) REFERENCES SantasLittleHelper(slhid) " 
																  + "ON DELETE CASCADE "
																  + "ON UPDATE CASCADE " 
																  + ");");

			createChild.executeUpdate();
			createHelper.executeUpdate();
			createGift.executeUpdate();
			createPresent.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Failed to create new tables!");
			System.exit(1);
		}
		System.out.println("4/5  Tables created");
	}
	
	
	/**
	 * Populate.
	 */
	private void populate() {
		System.out.println("4/5  Populating tables");
		try {

			PreparedStatement singleChild = conn
					.prepareStatement("INSERT INTO Child (cid, name, address) " + "VALUES (? , ?, ?); ");
			for (int i = 1; i <= 1000; i++) {
				singleChild.setInt(1, i);
				singleChild.setString(2, ("John " + i));
				singleChild.setString(3, i + " Birstol Road");
				singleChild.executeUpdate();
			}

			PreparedStatement singleHelper = conn
					.prepareStatement("INSERT INTO SantasLittleHelper (slhid, name) " + "VALUES (? , ?); ");
			for (int i = 1; i <= 100; i++) {
				singleHelper.setInt(1, i);
				singleHelper.setString(2, ("Snowy " + i));
				singleHelper.executeUpdate();
			}

			PreparedStatement singleGift = conn
					.prepareStatement("INSERT INTO Gift (gid, description) " + "VALUES (? , ?); ");
			for (int i = 1; i <= 100; i++) {
				singleGift.setInt(1, i);
				singleGift.setString(2, ("Soft and fluffy bear with sound " + i));
				singleGift.executeUpdate();
			}

			PreparedStatement singlePresent = conn
					.prepareStatement("INSERT INTO Present (gid, cid, slhid) " + "VALUES (? , ?, ?); ");
			for (int i = 1; i <= 100; i++) {
				singlePresent.setInt(1, i);
				singlePresent.setInt(2, i);
				singlePresent.setInt(3, i);
				singlePresent.executeUpdate();

			}

			// sufficient realistic data
			singleChild.setInt(1, 1001);
			singleChild.setString(2, ("Sim Lucas"));
			singleChild.setString(3, "96 Pershore Road");
			singleChild.executeUpdate();

			singlePresent.setInt(2, 1001);
			for (int i = 101; i <= 151; i++) {
				singleGift.setInt(1, i);
				singleGift.setString(2, ("Chocolate bar with filling number- " + i));
				singleGift.executeUpdate();

				singleHelper.setInt(1, i);
				singleHelper.setString(2, "Little Santa " + i);
				singleHelper.executeUpdate();

				singlePresent.setInt(1, i);
				singlePresent.setInt(3, i);
				singlePresent.executeUpdate();
			}

			// another set of realistic data

			singleHelper.setInt(1, 200);
			singleHelper.setString(2, "Lead Helper Jean");
			singleHelper.executeUpdate();

			singlePresent.setInt(3, 200);
			for (int i = 1; i <= 10; i++) {
				for (int j = 1; j <= 5; j++) {
					singlePresent.setInt(1, i);
					singlePresent.setInt(2, j);
					singlePresent.executeUpdate();
				}
			}

		} catch (SQLException e) {
			System.out.println("Failed to populate tables!");
			System.exit(1);
		}

		System.out.println("5/5  Tables succesfully populated");
		System.out.println("Database is ready!");
		
	}

	
}
