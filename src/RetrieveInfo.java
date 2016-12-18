/*
 * 
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RetrieveInfo {
	
	/** The connection */
	private Connection conn;
	
	
	public RetrieveInfo(Connection conn	)
	{
		this.conn = conn;
	}
	
	public void printChildInfo(int cid)
	{
		ChildInfo childInfo = new ChildInfo();
		
		try {
			PreparedStatement childQuery = conn.prepareStatement("SELECT C.cid, C.name, C.address "
															   + "FROM Child C "
															   + "WHERE C.cid=? ;"); // 1001
		
			
			PreparedStatement giftsOfChildQuery = conn.prepareStatement("SELECT G.gid, G.description "
																	  + "FROM Present P, Gift G " 
																	  + "WHERE P.cid=? AND G.gid = P.gid ;");
			
			childQuery.setInt(1, cid);
			giftsOfChildQuery.setInt(1, cid);
			ResultSet childRes = childQuery.executeQuery();
			ResultSet giftRes  = giftsOfChildQuery.executeQuery();
			
			int childID;
			String name = null;
			String address = null;
			
			while(childRes.next())
			{
				childID =Integer.parseInt(childRes.getString("cid"));
				name = childRes.getString("name");
				address = childRes.getString("address");
				childInfo.setCid(childID);
				childInfo.setName(name);
				childInfo.setAddress(address);
			}
			
			int gid ;
			String desc = null;
			
			while(giftRes.next())
			{
				gid = Integer.parseInt(giftRes.getString("gid"));
				desc = giftRes.getString("description");
				childInfo.addGift(gid, desc);
			}
			
		} catch (SQLException e) {
			System.out.println("SQL exception");
			System.exit(1);
		}
	}
	
	
	public void printHelperInfo(int lshid)
	{
		HelperInfo childInfo = new HelperInfo();
	}
	
	/**
	 * Test.
	 */
	private void test() {
		try {
			PreparedStatement studentQuery = conn.prepareStatement("SELECT * FROM Child Where cid=1001 ");

			PreparedStatement gifts = conn.prepareStatement(
					"select G.gid, G.description from Present P, Gift G " + " where P.cid=1001 AND G.gid = P.gid");

			PreparedStatement helper = conn
					.prepareStatement("select H.slhid, H.name " + " from SantasLittleHelper H where H.slhid= 200");

			PreparedStatement child = conn
					.prepareStatement("select H.slhid, H.name " + " from SantasLittleHelper H where H.slhid= 200");

			ResultSet rs = studentQuery.executeQuery();
			ResultSet rs2 = gifts.executeQuery();

			String title = null;
			String gid = null;
			String desc = null;
			// Now interate through the books just picking up the title
			while (rs.next()) {
				title = rs.getString("name");
				System.out.println(title);
			}

			while (rs2.next()) {
				gid = rs2.getString("gid");
				desc = rs2.getString("description");
				System.out.println(gid + " " + desc);

			}
		} catch (SQLException sqlE) {
			System.out.println("SQL code is broken");

		}

		// Now, just tidy up by closing connection
		try {
			conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
