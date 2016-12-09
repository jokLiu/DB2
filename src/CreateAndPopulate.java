import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class CreateAndPopulate {

	
	private BufferedReader reader;
	private String name= "jxl706";
	private String psw= "jvrhx1oucf";

	public CreateAndPopulate(Connection conn)
	{
		//initializeRaeder();
		//readNamePsw();
		registerDriver();
		connectServer(conn);


	}
	
	
	private void readNamePsw()
	{
		String[] arg;
		try {
			arg =  reader.readLine().split(" ");
			name = arg[0];
			psw  = arg[1];
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("failed to read from the file");
		}
		
		
	}
	
	private void initializeRaeder()
	{
		try {
			File file = new File("db-password.txt");	
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File not found!");
		}
	}

	private void registerDriver()
	{
		try{
			System.setProperty("jdbc.drivers","org.postgresql.Driver");
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e ){
			System.out.println("Driver not found");
		}
		
		System.out.println("PostgreSQL driver registered.");
	}

	private void connectServer(Connection conn)
	{
		try{
			conn = DriverManager.getConnection("jdbc:postgresql://mod-intro-databases/cslibrary", name, psw);
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		System.out.println(conn!=null ? "Database accessed!" : "Failed to make connection");
	}
}
