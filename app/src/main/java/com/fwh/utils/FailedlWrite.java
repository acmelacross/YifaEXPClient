package com.fwh.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FailedlWrite {

	private static String errorPathName="yifaclient";
	/**
	 * 
	 * @param ex
	 * 将崩溃写入文件系统
	 */
	public static void writeCrashInfoToFile(String failedStr) {
		//String result = failedStr.toString();
		//这里把刚才异常堆栈信息写入SD卡的Log日志里面

			String sdcardPath = Environment.getExternalStorageDirectory().getPath();
			String filePath = sdcardPath  +"/"+errorPathName+"/failed/";
			 writeLog(failedStr, filePath);
	}

	/**
	 * 
	 * @param log
	 * @param name
	 * @return 返回写入的文件路径
	 * 写入Log信息的方法，写入到SD卡里面
	 */
	private static String writeLog(String log, String name) 
	{
		//TCAgent.onError(, Throwable throwable)

		 DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String time = formatter.format(new Date());

		File filePath = new File(name+time);
		File fileName = new File(name+"/"+time+"/"+time+".txt");
//		System.out.println("filename  + "   + name);
		if(!filePath.exists()){
			filePath.mkdirs();
			System.out.println("filePath.mkdirs()  "+filePath.mkdirs());
		}
		try 
		{
			Log.d("TAG", "写入到SD卡里面" + log);


			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(fileName),"UTF-8");
			out.write(log);


			out.flush();
			out.close();

			
			return name+time;
		} 
		catch (IOException e) 
		{
			//Log.e(TAG, "an error occured while writing file...", e);
			e.printStackTrace();
			return null;
		}
	}
}
