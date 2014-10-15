package com.pramati.archiver;

import org.jsoup.nodes.Document;

public interface Archiver {
	
	public void archive(Document doc, String url);
	public void archive(String url);
}
