package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

	public static void main(String[] args) {
		System.out.println(DbConnection.getDb());

	}
	public static Connection getDb() 
	{
		Connection conn=null;
		String url="jdbc:mysql://localhost:3306/companyd5";		
		String user="root";
		//String password="1234";
		String password="12345678";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");			
			conn=DriverManager.getConnection(url,user,password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

}
