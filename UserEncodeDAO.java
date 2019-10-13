package iNTuition.ternary;

import java.sql.*;

public class UserEncodeDAO {
	private Connection con = null;
	private String user_name="";
	
	public UserEncodeDAO(String user_name) {
		this.user_name=user_name;
	}
	
	public void setUp() throws SQLException, ClassNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/intuition", "root", "duykhanh.792000");
			System.out.println("SQL connection successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement stmt= con.createStatement();
		String createAuth= "CREATE TABLE IF NOT EXISTS "+ user_name+" ("
				        +  " filePath varchar(255),"
						+  " imagePath varchar(255),"
						+  " encodeKey int )";
		try {
			stmt.execute(createAuth);
			System.out.println("Create encode table for "+user_name+" successfully");
		}
		catch(Exception e) {
			System.out.println("Failed to create encode table for "+user_name);
		}
	}
	
	public boolean insert(String filePath, String imagePath, int key) throws ClassNotFoundException, SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/intuition", "root", "duykhanh.792000");
			System.out.println("SQL connection successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//check whether imagePath exists in table
		Statement stmt2= con.createStatement();
		String checkImage="SELECT * FROM "+this.user_name+" WHERE imagePath= '"+imagePath+"'";
		try {
			ResultSet count= stmt2.executeQuery(checkImage);
			//if image has existed, do not need to insert again (return false)
			if(count.next()) {
				System.out.println("Image exists");
				return false;
			}
				
		}
		catch(Exception e) {
			System.out.println("Cannot check whether image pat exists or not");
			return false;
		}
		
		
		// store record
		Statement stmt1= con.createStatement();
		String insertEncodeRecord="INSERT INTO "+this.user_name+" (filePath,imagePath,encodeKey) VALUES ('"+filePath+"','"+imagePath+"',"+key+")";
		try {
			stmt1.executeUpdate(insertEncodeRecord);
			System.out.println("Insert successfully");
			return true;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public int getKey(String imagePath) throws ClassNotFoundException, SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/intuition", "root", "duykhanh.792000");
			System.out.println("SQL connection successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Statement stmt= con.createStatement();
		String searchKey="SELECT * FROM "+this.user_name+" WHERE imagePath= '"+imagePath+"'";
		try {
			ResultSet result= stmt.executeQuery(searchKey);
			result.next();
			int key= Integer.parseInt(result.getString("encodeKey"));
			return key;
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}

}
