package com.pramati.archiver;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

import com.pramati.handler.FileNameHandler;

public class PageArchiver implements Archiver {

	private FileNameHandler fileNameHandler;

	private String location;

	public void archive(Document doc, String url) {
		
		try {
			
			String fileName = fileNameHandler.getNameUrlMap().get(url);
			PrintWriter docWriter = new PrintWriter(new File(location
					+ File.separator + fileName));
			docWriter.write(doc.toString());
			docWriter.flush();
			docWriter.close();
			
		} catch (IOException e) {		
			System.out.println("An error occured while saving the document");
			e.printStackTrace();
		}
	}

	public void setLocation(String location) {	
		this.location = location;
	}

	public String getLocation(){	
		return location;
	}
	public void archive(String url) {
		//Default Implementation
	}

	@Autowired
	public void setFileNameHandler(FileNameHandler fileNameHandler) {
		this.fileNameHandler = fileNameHandler;
	}
	
	
}
