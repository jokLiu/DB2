package database.jdbc;

import java.sql.Connection;

import javax.swing.JFrame;

import database.createAndPopulate.Connect;

public class manageDBmain {

	public static void main(String[] args) {
		
		String name = "jxl706";
		String psw = "jvrhx1oucf";
		
		//connecting to database
		Connect connection = new Connect(name, psw);
		Connection conn = connection.getConnection();
		
		//class for querying and managing database
		RetrieveInfo info = new RetrieveInfo(conn);
		
		//GUI for the database
		View view = new View(conn);
		InfoController cont = new InfoController(info, view);
		view.registerListener(cont);
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setSize(400, 300);
		view.setVisible(true);
		view.pack();

	}

}
