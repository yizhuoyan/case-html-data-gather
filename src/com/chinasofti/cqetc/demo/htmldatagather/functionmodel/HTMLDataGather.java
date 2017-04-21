package com.chinasofti.cqetc.demo.htmldatagather.functionmodel;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.chinasofti.cqetc.demo.htmldatagather.datamodel.DuYinDM;
import com.chinasofti.cqetc.demo.htmldatagather.datamodel.HanZiDM;

public class HTMLDataGather {
	private final int timeout = 10000;
	
	public HanZiDM from(String url) throws Exception {
		Document doc = Jsoup.connect(url).timeout(timeout).get();
		HanZiDM dm=this.parse(doc);
		if(dm==null){
			throw new RuntimeException();
		}
		return dm;
	}

	private HanZiDM parse(Document doc) throws Exception {
		Element contentEL = doc.getElementById("tagContent0");
		String wubi=this.gatherWuBi(contentEL);
		wubi=checkNotNone("无法找到五笔节点内容", wubi);
		String bihua=this.gatherBiHua(contentEL);
		bihua=checkNotNone("无法找到笔画节点内容", bihua);
		List<DuYinDM> duYins=this.gatherDuyins(contentEL);
		HanZiDM dm=new HanZiDM();
		dm.setWubi(wubi);
		dm.setBihua(bihua.length());
		dm.setDuyins(duYins);
		return dm;
	}
	private String checkNotNone(String message,String s){
		if(s==null||(s=s.trim()).length()==0){
			throw new IllegalArgumentException(message);
		}
		return s;
	}

	
	private String gatherWuBi(Element tagContentEL) {
		Elements spans = tagContentEL.select("span.diczx7");
		for (Element span : spans) {
			if (span.text().equals("五笔:")) {
				// 后一个兄弟文本节点
				Node textNode = span.nextSibling();
				if (textNode instanceof TextNode) {
					String wubi=((TextNode) textNode).text();
					//去掉特殊字符
					wubi=wubi.replaceAll("\\W", "");
					return wubi;
				}
			}
		}
		return null;
	}
	private String gatherBiHua(Element tagContentEL) {
		Elements spans = tagContentEL.select("span.diczx6");
		for (Element span : spans) {
			if (span.text().equals("笔顺编号:")) {
				// 后一个兄弟文本节点
				Node textNode = span.nextSibling();
				if (textNode instanceof TextNode) {
					return ((TextNode)textNode).getWholeText();
				}
			}
		}
		return null;
	}
	private List<DuYinDM> gatherDuyins(Element contentEL)throws Exception{
		Elements elements=contentEL.select("p");
		DuYinDM dm=null;
		List<DuYinDM> results=new ArrayList<DuYinDM>(3);
		for (Element p : elements) {
			if(p.children().isEmpty())continue;
			Element firstChild=p.child(0);
			if("span".equals(firstChild.tagName())){
				if(firstChild.hasClass("dicpy")){
					if(dm!=null){
						results.add(dm);
					}
					dm=new DuYinDM();
					String duyin=firstChild.text();
					dm.setDuyin(duyin);
				}
			}else 	if("em".equals(firstChild.tagName())){
				
				StringBuilder ziyi=new StringBuilder();
				Node next=firstChild.nextSibling();
				while(next!=null){
					if(next instanceof TextNode){
						ziyi.append(((TextNode) next).text());
					}else if(next instanceof Element){
						ziyi.append(((Element) next).text());
					}
					next=next.nextSibling();
				}
				dm.addZiyi(ziyi.toString());
			}
		}
		if(dm!=null){
			results.add(dm);
		}
		return results;
	}
}
