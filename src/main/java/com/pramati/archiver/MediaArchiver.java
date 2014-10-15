package com.pramati.archiver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

import com.pramati.handler.MediaFileNameHandler;

public class MediaArchiver implements Archiver {

	@Autowired
	MediaFileNameHandler mediaFileNameHandler;

	private String location;

	public void archive(String url) {
		try {
			File file = new File(location);
			if (!file.exists()) {
				file.mkdir();
			}
			String fileName = mediaFileNameHandler.getNameUrlMap().get(url);
			Response resultImageResponse = Jsoup.connect(url)
					.ignoreContentType(true).execute();

			FileOutputStream out = (new FileOutputStream(location
					+ File.separator + fileName));
			out.write(resultImageResponse.bodyAsBytes());
			out.close();
		} catch (IOException e) {
			
			System.out.println("An error occured while saving the images"
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void archive(Document doc, String url) {
		// Not Implemented
	}
}
