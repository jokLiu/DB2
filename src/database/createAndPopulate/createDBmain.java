package database.createAndPopulate;

import java.sql.Connection;

public class createDBmain {

	public static void main(String[] args) {
		String name = "jxl706";
		String psw = "jvrhx1oucf";
		
		//connecting to the database
		Connect connection = new Connect(name, psw);
		Connection conn = connection.getConnection();
				
		//creating and populating the database
		CreateAndPopulate cr = new CreateAndPopulate(conn);
		cr.prepareDatabse();
		
		//closing the connection
		connection.closeConnection();

	}

}
