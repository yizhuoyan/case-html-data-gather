package com.chinasofti.cqetc.demo.htmldatagather.datamodel;

import java.util.LinkedList;
import java.util.List;

/**
 * 汉字字义
 * 一个汉字可能是多音字,每个读音有不同的字义
 * @author Administrator
 * 
 */
public class DuYinDM {
	/**对应读音*/
	private String duyin;
	/**读音对应字义*/
	private List<String> ziyis;
	public String getDuyin() {
		return duyin;
	}
	public void setDuyin(String duyin) {
		this.duyin = duyin;
	}
	public List<String> getZiyis() {
		return ziyis;
	}
	public void addZiyi(String ziyi) {
		if(this.ziyis==null){
			this.ziyis=new LinkedList<String>();
		}
		this.ziyis.add(ziyi);
	}
	@Override
	public String toString() {
		return "DuYinDM [duyin=" + duyin + ", ziyis=" +ziyis + "]";
	}
    
	
}
