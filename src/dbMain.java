import java.sql.Connection;

import javax.swing.JFrame;

public class dbMain {

	public static void main(String[] args) {
		Connection conn =null ;	
		String name = "jxl706";
		String psw = "jvrhx1oucf";
		
		Connect connection = new Connect(conn, name, psw);
		conn = connection.getConnection();
		
		
		CreateAndPopulate cr = new CreateAndPopulate(conn);
		
		RetrieveInfo info = new RetrieveInfo(conn);
		View view = new View();
		
		
		InfoController cont = new InfoController(info, view);
		view.registerListener(cont);

		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setSize(400, 300);
		view.pack();
		view.setVisible(true);
	}

}
