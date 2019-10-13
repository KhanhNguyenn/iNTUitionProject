package iNTuition.ternary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthenticateDAO {
	private Connection con=null;
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
		String createAuth= "CREATE TABLE IF NOT EXISTS auth ("
				        +  " id int PRIMARY KEY AUTO_INCREMENT,"
						+  " user_name varchar(50),"
						+  " password varchar(30),"
						+  " email varchar(100),"
						+  " authImage varchar(255) )";
		try {
			stmt.execute(createAuth);
			System.out.println("Create auth table successfully");
		}
		catch(Exception e) {
			System.out.println("Failed to create auth table");
		}
		
	}
	public boolean check(String user_name, String password) throws ClassNotFoundException, SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/intuition", "root", "duykhanh.792000");
			System.out.println("SQL connection successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement stmt= con.createStatement();
		String selectAuth= "SELECT * FROM auth WHERE user_name= '"+user_name+"' AND password= '"+password+"'";
		ResultSet count= stmt.executeQuery(selectAuth);
		//if not exists
		if(!count.next()) {
			return false;
		}
		else {
			return true;
		}
	}
	public boolean insert(String user_name, String password, String email) throws ClassNotFoundException, SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/intuition", "root", "duykhanh.792000");
			System.out.println("SQL connection successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement stmt= con.createStatement();
		String  createAuth="INSERT INTO auth (user_name,password,email) VALUES ('"+user_name+"','"+password+"','"+email+"')";
		try {
			stmt.executeUpdate(createAuth);
			System.out.println("Insert successfully");
			return true;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean insertImage(String user_name, String authImage) throws ClassNotFoundException, SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/intuition", "root", "duykhanh.792000");
			System.out.println("SQL connection successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Statement stmt= con.createStatement();
		String insertAuthImage="UPDATE auth SET authImage= '"+authImage+"' WHERE user_name= '"+user_name+"'";
		try {
			stmt.executeUpdate(insertAuthImage);
			System.out.println("Insert auth image successfully");
			return true;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public List<String> getImageList() throws ClassNotFoundException, SQLException{
		List<String> imageList= new ArrayList<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/intuition", "root", "duykhanh.792000");
			System.out.println("SQL connection successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Statement stmt= con.createStatement();
		String selectImage="SELECT authImage FROM auth";
		try {
			ResultSet result= stmt.executeQuery(selectImage);
			while(result.next()) {
				imageList.add(result.getString("authImage"));
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return imageList;
	}
	
	public String getNameFromImg(String authImage) throws ClassNotFoundException, SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/intuition", "root", "duykhanh.792000");
			System.out.println("SQL connection successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String temp="";
		for(int i=0;i<authImage.length();i++) {
			char ch= authImage.charAt(i);
			if(ch=='\\') {
				temp+="\\\\";
			}
			temp+=ch;
		}
		authImage=temp;
		Statement stmt= con.createStatement();
		String selectName="SELECT user_name FROM auth WHERE authImage= '"+authImage+"'";
		ResultSet result= stmt.executeQuery(selectName);
		result.next();
		String user_name= result.getString("user_name");
		return user_name;
		
	}

}
