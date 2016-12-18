import java.sql.Connection;

public class dbMain {

	public static void main(String[] args) {
		Connection conn =null ;	
		String name = "jxl706";
		String psw = "jvrhx1oucf";
		
		Connect connection = new Connect(conn, name, psw);
		conn = connection.getConnection();
		
		
		CreateAndPopulate cr = new CreateAndPopulate(conn);
		
		RetrieveInfo info = new RetrieveInfo(conn);
		info.printChildInfo(1001);
	}

}
