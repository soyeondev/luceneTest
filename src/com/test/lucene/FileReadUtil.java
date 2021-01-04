package com.test.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileReadUtil {
	
	/**
	 * 파일 읽어서 ArrayList 로 리턴
	 * 
	 * @param file
	 * @param encode
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static ArrayList<String> readFile(File file, String encode) throws IOException, Exception {
		if (file == null || !file.exists()) {
			return null;
		}

		ArrayList<String> resultList = null;

		FileInputStream fileInputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;

		try {
			fileInputStream = new FileInputStream(file);
			inputStreamReader = new InputStreamReader(fileInputStream, encode);
			bufferedReader = new BufferedReader(inputStreamReader);

			String oneLine = null;
			while ((oneLine = bufferedReader.readLine()) != null) {
				if (resultList == null) {
					resultList = new ArrayList<String>();
				}

				resultList.add(oneLine);
			}

		} catch (IOException e) {
			throw e;

		} catch (Exception e) {
			throw e;

		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (Exception e) {
				bufferedReader = null;
			}

			try {
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
			} catch (Exception e) {
				inputStreamReader = null;
			}

			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (Exception e) {
				fileInputStream = null;
			}
		}

		return resultList;
	}
	
	
	/**
	 * 파일 읽어서 String 으로 리턴
	 * 
	 * @param file
	 * @param encode
	 * @param delimiter
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String readFileToString(File file, String encode, String delimiter) throws IOException, Exception {
		StringBuffer buff = new StringBuffer();
		
		ArrayList<String> strList = readFile(file, encode);
		if (strList != null && strList.size() > 0) {
			int listCount = strList.size();
			for (int i=0; i<listCount; i++) {
				buff.append(strList.get(i));
				buff.append(delimiter);
			}
		}
		
		return buff.toString();
	}
	
	
	/**
	 * fileList 객체에 텍스트 파일을 담는다.
	 * fileObj 객체가 폴더일 경우 하위의 모든 텍스트 파일을 담는다.
	 * 
	 * @param fileList
	 * @param file
	 * @throws Exception
	 */
	public static void addAllTextFiles(ArrayList<String> fileList, File fileObj) throws Exception {
		if (fileList == null) {
			return;
		}
		
		if (fileObj == null) {
			return;
		}
		
		if (!fileObj.exists()) {
			return;
		}
		
		if (fileObj.isDirectory()) {
			File[] fileArr = fileObj.listFiles();
			if (fileArr != null && fileArr.length > 0) {
				int fileCount = fileArr.length;
				for (int i=0; i<fileCount; i++) {
					addAllTextFiles(fileList, fileArr[i]);
				}
			}
			
		} else if (fileObj.isFile()) {
			if (fileObj.getCanonicalPath() != null && fileObj.getCanonicalPath().toLowerCase().endsWith(".txt")) {
				fileList.add(fileObj.getCanonicalPath());
			}
		}
	}
}
