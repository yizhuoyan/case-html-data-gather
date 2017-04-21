package com.chinasofti.cqetc.demo.htmldatagather.dao;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	static{
		init();
	}
	private static final String URL="jdbc:mysql://127.0.0.1:3306/hanzi";
	private static final String USER="root",PASSWORD="root";
	private static void init(){
		try{
			//Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static Connection getConnection(){
		try{
		Connection connection=DriverManager.getConnection(URL,USER,PASSWORD);
		return connection;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) throws Exception{
		Connection con=DBUtil.getConnection();
	//	PreparedStatement s=con.prepareStatement("insert into test values(?)");
	//	s.setString(1, "𪓰");
		//s.executeUpdate();
		PreparedStatement s=con.prepareStatement("select * from test limit 0,1");
		ResultSet rs=s.executeQuery();
		if(rs.next()){
			String data=rs.getString(1);
			System.out.println(data);
		}
		 con.close();
	}
}
