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

	private MediaFileNameHandler mediaFileNameHandler;

	private String location;

	public void archive(String url) {

		FileOutputStream mediaWriter = null;
		try {

			File locationFolder = new File(location);

			if (!locationFolder.exists()) {

				boolean isCreated = locationFolder.mkdir();
				if (!isCreated)
					throw new RuntimeException(
							"creation of media directory failed!");
			}

			String fileName = mediaFileNameHandler.getNameUrlMap().get(url);
			Response resultImageResponse = Jsoup.connect(url)
					.ignoreContentType(true).execute();

			mediaWriter = (new FileOutputStream(location
					+ File.separator + fileName));
			mediaWriter.write(resultImageResponse.bodyAsBytes());
			mediaWriter.flush();
			mediaWriter.close();

		} catch (IOException e) {

			System.out.println("An error occured while saving the image "
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void archive(Document doc, String url) {
		// Default Implementation
	}

	@Autowired
	public void setMediaFileNameHandler(
			MediaFileNameHandler mediaFileNameHandler) {
		this.mediaFileNameHandler = mediaFileNameHandler;
	}

}
