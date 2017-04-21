package com.chinasofti.cqetc.demo.htmldatagather.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.chinasofti.cqetc.demo.htmldatagather.datamodel.DuYinDM;
import com.chinasofti.cqetc.demo.htmldatagather.datamodel.HanZiDM;

public class HanZiDao {
	private Connection connection;
	private PreparedStatement hanziPS,duyinPS;

	public HanZiDao(Connection con) {
		this.connection = con;
		this.init();
	}

	private void init() {
		try {
			hanziPS = connection.prepareStatement(getInsertHanZiSQL());
			duyinPS = connection.prepareStatement(getInsertDuYinSQL());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	private String getInsertHanZiSQL() {
		return new StringBuilder(80).append("insert into hanzi_list")
				.append("(unicode,name,wubi,bihua)values")
				.append("(?,?,?,?)").toString();
	}
	private String getInsertDuYinSQL() {
		return new StringBuilder(80).append("insert into hanzi_duyin")
				.append("(unicode,duyin,ziyi)values")
				.append("(?,?,?)").toString();
		
	}
	public void insert(HanZiDM t)throws SQLException{
		try{
			int i=1;
			hanziPS.setInt(i++, t.getUnicode());
			hanziPS.setString(i++, t.getName());
			hanziPS.setString(i++, t.getWubi());
			hanziPS.setInt(i++, t.getBihua());
			hanziPS.executeUpdate();
			List<DuYinDM> duyins=t.getDuyins();
			if(duyins!=null){	
				this.insertsDuyin(t,duyins);
			}
		}catch(SQLException e){
			throw e;
		}
	}
	private void insertsDuyin(HanZiDM hanzi,List<DuYinDM> duyins)throws SQLException{
			for (DuYinDM d : duyins) {
				int i=1;
				duyinPS.setInt(i++, hanzi.getUnicode());
				duyinPS.setString(i++, d.getDuyin());
				String ziyi=String.valueOf(d.getZiyis());
				duyinPS.setString(i++, ziyi);
				duyinPS.executeUpdate();
			}
	}
	public static void main(String[] args) throws Exception{
		System.out.println("𪓰".getBytes("utf-8").length);
		
	}
}
