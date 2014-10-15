package com.pramati.handler;

import java.util.HashMap;
import java.util.Map;

public class MediaFileNameHandler {
	private int file_suffix = 0;
	private Map<String, String> nameUrlMap = new HashMap<String, String>();

	public String mapUrlToFile(String url) {
		String fileName = nameUrlMap.get(url);
		if (fileName == null) {
			file_suffix = file_suffix + 1;
			fileName = "image_" + file_suffix + ".png";
			nameUrlMap.put(url, fileName);
		}
		return fileName;
	}

	public Map<String, String> getNameUrlMap() {
		return nameUrlMap;
	}
}
