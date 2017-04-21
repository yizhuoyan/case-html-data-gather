package com.chinasofti.cqetc.demo.htmldatagather.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * 汉字
 * @author 
 *
 */
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class HanZiDM {
	/**unicdoe编码*/
	private int unicode;
	/**汉字本身*/
	private String name;
	/**读音,一个汉字可能是多音字,每个读音有各自的字义*/
	private List<DuYinDM> duyins;
	/**五笔*/
	private String wubi;
	/**笔画数*/
	private int bihua;
	
	public int getUnicode() {
		return unicode;
	}
	public void setUnicode(int unicode) {
		this.unicode = unicode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<DuYinDM> getDuyins() {
		return duyins;
	}
	public void addDuyin(DuYinDM duyin) {
		if(this.duyins==null){
			this.duyins=new ArrayList<DuYinDM>(3);
		}
		this.duyins.add(duyin);
	}
	public void setDuyins(List<DuYinDM> duyins){
		this.duyins=duyins;
	}
	public String getWubi() {
		return wubi;
	}
	public void setWubi(String wubi) {
		this.wubi = wubi;
	}
	public int getBihua() {
		return bihua;
	}
	public void setBihua(int bihua) {
		this.bihua = bihua;
	}
	@Override
	public String toString() {
		return "HanZiDM [unicode=" + unicode + ", name=" + name + ", duyins=" + duyins + ", wubi=" + wubi + ", bihua="
				+ bihua+"]";
	}
	
	
	
}
