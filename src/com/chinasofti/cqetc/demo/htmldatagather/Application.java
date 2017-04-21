package com.chinasofti.cqetc.demo.htmldatagather;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import com.chinasofti.cqetc.demo.htmldatagather.dao.DBUtil;
import com.chinasofti.cqetc.demo.htmldatagather.dao.HanZiDao;
import com.chinasofti.cqetc.demo.htmldatagather.datamodel.HanZiDM;
import com.chinasofti.cqetc.demo.htmldatagather.functionmodel.HTMLDataGather;

public class Application {
	// 错误处理url
	private List<String> errorURL = new LinkedList<String>();
	// 已处理队列
	private final BlockingQueue<HanZiDM> queue = new LinkedBlockingQueue<HanZiDM>();
	// 采集器
	private final HTMLDataGather gather = new HTMLDataGather();
	// 采集基础url定义
	private final String baseURL = "http://www.zdic.net/z/jd/?u=";
	private Connection connection = DBUtil.getConnection();
	private HanZiDao dao = new HanZiDao(connection);
	private Thread persistThread;
	
	
	public void gatherOne(char c) {
		HanZiDM dm = null;
		String url = null;
		try {
			url = baseURL + Integer.toHexString(c);
			dm = gather.from(url);
		} catch (Exception e) {
			e.printStackTrace();
			// 记录错误汉字,后期处理
			errorURL.add(url);
			return;
		}
		dm.setUnicode(c);
		dm.setName(String.valueOf(c));
		try {
			queue.put(dm);
		} catch (InterruptedException e) {
		}
	}

	public void run() {
		
		persistThread=new Thread(){
			public void run() {
				while(!Thread.interrupted()){
					try{
					persistRun();
					}catch(Exception e){
						e.printStackTrace();
						System.exit(0);
					}
				}
			};
		};
		persistThread.start();
		gatherRun();
	}

	private void gatherRun() {

		for (char c = 0x4e00; c <= 0x9fa5; c++) {
					gatherOne(c);
		}
		persistThread.interrupt();
	}

	private void persistRun()throws Exception {
		HanZiDM dm = queue.take();
		try {
			connection.setAutoCommit(false);
			dao.insert(dm);
			System.out.println(dm);
			connection.commit();
		} catch (Exception e) {
			System.out.println(dm);
			connection.rollback();
			throw e;
		}
	}

	public static void main(String[] args) throws Exception {
		Application app = new Application();
		app.run();
		System.out.println(app.errorURL);
	}
}
