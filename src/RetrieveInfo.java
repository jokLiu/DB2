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
	
	public ChildInfo printChildInfo(int cid)
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
		
		return childInfo;
	}
	
	
	public HelperInfo printHelperInfo(int lshid)
	{
		HelperInfo helperInfo = new HelperInfo();
		
		try {
			PreparedStatement helper = conn.prepareStatement("select H.slhid, H.name " + " from SantasLittleHelper H where H.slhid= ?");
			
			
			PreparedStatement child = conn
					.prepareStatement("SELECT Ch.cid, Ch.name, Ch.address "
									+ " FROM Child Ch "
									+ "WHERE Ch.cid IN "
										+ "(SELECT C.cid "
							   			+ "FROM Child C, Present P "
							   			+ "WHERE P.slhid = ? AND P.cid = C.cid);");
			
			PreparedStatement presents = conn.prepareStatement("SELECT G.gid, G.description "
															 + "FROM Gift G, Present P "
															 + "WHERE P.cid = ? AND P.slhid = ? AND G.gid = P.gid ;");
			
			presents.setInt(2, lshid);
			
			helper.setInt(1, lshid);
			child.setInt(1, lshid);
			ResultSet helperRes = helper.executeQuery();
			ResultSet childRes = child.executeQuery();
			
			
			int hid ;
			String name;
			
			while(helperRes.next())
			{
				hid = Integer.parseInt(helperRes.getString("slhid"));
				name = helperRes.getString("name");
				helperInfo.setSlhid(hid);
				helperInfo.setName(name);
			}
			
			int cid;
			String chName = null;
			String address = null;
			ChildInfo chInfo;
			while(childRes.next())
			{
				chInfo = new ChildInfo();
				
				cid = Integer.parseInt(childRes.getString("cid"));
				chName = childRes.getString("name");
				address = childRes.getString("address");
				
				chInfo.setCid(cid);
				chInfo.setName(chName);
				chInfo.setAddress(address);

				presents.setInt(1, cid);
				ResultSet childPres = presents.executeQuery();
				while(childPres.next())
				{
					int gid ;
					String desc = null;
					gid = Integer.parseInt(childPres.getString("gid"));
					desc = childPres.getString("description");
					chInfo.addGift(gid, desc);
				}
				
				helperInfo.addChildInfo(chInfo);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return helperInfo;

	}
	
	
}
