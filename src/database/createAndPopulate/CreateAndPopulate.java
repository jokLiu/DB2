package database.createAndPopulate;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

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
	}
	
	
	
	/**
	 * Prepare database for use
	 * -delete tables
	 * -create a new ones
	 */
	public void prepareDatabse()
	{
		deleteTables();
		newTables();
		
	}


	/**
	 * Delete previous tables from the database (clean database)
	 */
	private void deleteTables() {
		System.out.println("Deleting all the tables");

		try {
			//Dropping the schema of the database
			PreparedStatement del = conn.prepareStatement("DROP SCHEMA public CASCADE;");
			del.execute();

			//Recreating the schema
			PreparedStatement setClean = conn.prepareStatement("CREATE SCHEMA public;");
			setClean.execute();

		} catch (SQLException e) {
			System.out.println("Failed to delete tables ");
			System.exit(1);
		}
		System.out.println("Successfully cleaned the tables");
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
		System.out.println("Creating new tables");
		try {
			//statement for child table
			PreparedStatement createChild = conn.prepareStatement(  "CREATE TABLE Child("
																  + "cid 		INTEGER," 
																  + "name 		TEXT 		NOT NULL," 
																  + "address 	TEXT 		NOT NULL," 
																  + "PRIMARY KEY (cid) " 
																  + ");");
			
			
			
			//statement for SantasLittleHelper table
			PreparedStatement createHelper = conn.prepareStatement( "CREATE TABLE SantasLittleHelper(" 
																  + "slhid	INTEGER," 
																  + "name 	TEXT 	NOT NULL,"
																  + "PRIMARY KEY (slhid)" 
																  + ");");
			
			//statement for Gift table			
			PreparedStatement createGift = conn.prepareStatement(   "CREATE TABLE Gift(" 
															      + "gid		 INTEGER," 
															      + "description TEXT 	NOT NULL," 
															      + "PRIMARY KEY (gid)"
															      + " );" ); 
			
			//statement for present table		
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
			//executing all the statements
			createChild.executeUpdate();
			createHelper.executeUpdate();
			createGift.executeUpdate();
			createPresent.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("Failed to create new tables!");
			System.exit(1);
		}
		System.out.println("Tables created");
	}
	
	
	/**
	 * Gets the names.
	 *
	 * @return the names
	 */
	private String[] getNames()
	{
		String[] names = new String[32];
		int trackItem = 0;
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader("src/database/createAndPopulate/names.txt"));
			
			String temp;
			while((temp = reader.readLine()) != null)
			{
				names[trackItem] =temp;
				trackItem++;
			}
			reader.close();
			
		} catch (IOException e) {
			System.err.println("Failed to read from file");
			System.exit(1);
		} 
		return names;
	}
	
	
	/**
	 * Populate.
	 */
	private void populate() {
		System.out.println("Populating tables");
		try {
			//32 random names
			String[] names = getNames();

			//Creating 1024 children 
			PreparedStatement singleChild = conn
					.prepareStatement("INSERT INTO Child (cid, name, address) " + "VALUES (? , ?, ?); ");
			
			
			for (int i = 1; i <= names.length; i++) {
				for(int j = 1; j<= names.length; j++){
					int id = ((i-1)*names.length)+j;
					singleChild.setInt(1, id);
					singleChild.setString(2, names[i-1] + " " + names[j-1]);
					singleChild.setString(3, id + " Birstol Road");
					singleChild.executeUpdate();
				}
			}

			//Creating 32 Santa's Little helpers
			PreparedStatement singleHelper = conn
					.prepareStatement("INSERT INTO SantasLittleHelper (slhid, name) " + "VALUES (? , ?); ");
			for (int i = 1; i <= names.length; i++) {
				singleHelper.setInt(1, i);
				singleHelper.setString(2, names[i-1]);
				singleHelper.executeUpdate();
			}

			//Creating 100 Gifts
			PreparedStatement singleGift = conn
					.prepareStatement("INSERT INTO Gift (gid, description) " + "VALUES (? , ?); ");
			for (int i = 1; i <= 100; i++) {
				singleGift.setInt(1, i);
				singleGift.setString(2, ("Soft and fluffy bear with sound " + i));
				singleGift.executeUpdate();
			}

			//Creating 100 Presents
			PreparedStatement singlePresent = conn
					.prepareStatement("INSERT INTO Present (gid, cid, slhid) " + "VALUES (? , ?, ?); ");
			for (int i = 1; i <= 100; i++) {
				singlePresent.setInt(1, i);
				singlePresent.setInt(2, i);
				singlePresent.setInt(3, (new Random()).nextInt(names.length-1)+2);
				singlePresent.executeUpdate();
			}

			// sufficient realistic data for a single child
			singleChild.setInt(1, 1025);
			singleChild.setString(2, ("Sim Lucas"));
			singleChild.setString(3, "96 Pershore Road");
			singleChild.executeUpdate();

			singlePresent.setInt(2, 1025);
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

			// another set of realistic data for helper

			singleHelper.setInt(1, 33);
			singleHelper.setString(2, "Lead Helper Jean");
			singleHelper.executeUpdate();

			singlePresent.setInt(3, 33);
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

		System.out.println("Tables succesfully populated");
		System.out.println("Database is ready!");
		
	}
	
	
}
