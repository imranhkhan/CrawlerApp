package com.pramati.handler;

import java.util.HashMap;
import java.util.Map;

public class FileNameHandler {

	private int file_suffix = 0;
	private Map<String, String> nameUrlMap = new HashMap<String, String>();

	public String mapUrlToFile(String url) {
		String fileName = nameUrlMap.get(url);
		if (fileName == null & url != null) {
			String[] tokens = url.split("http://");
			if (tokens.length > 1) {
				fileName = tokens[1].replaceAll("/", "").replaceAll("\\.", "")+".html";		
			} else {
				file_suffix = file_suffix + 1;
				fileName = "index_" + file_suffix + ".html";
			}
			nameUrlMap.put(url, fileName);
		}
		return fileName;
	}

	public Map<String, String> getNameUrlMap() {
		return nameUrlMap;
	}
}
