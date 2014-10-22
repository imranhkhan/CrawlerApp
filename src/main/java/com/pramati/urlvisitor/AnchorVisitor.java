package com.pramati.urlvisitor;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import com.pramati.archiver.Archiver;
import com.pramati.archiver.MediaArchiver;
import com.pramati.archiver.PageArchiver;
import com.pramati.handler.FileNameHandler;
import com.pramati.handler.MediaFileNameHandler;

public class AnchorVisitor implements UrlVisitor {

	private FileNameHandler fileNameHandler;

	private MediaFileNameHandler mediaFileNameHandler;

	private Archiver pageArchiver;

	private Archiver mediaArchiver;

	private List<String> visitedUrls;

	public int visit(ArrayDeque<String> urlQueue) {
		
		visitedUrls = new ArrayList<String>();
		
		//Set the media path
		((MediaArchiver)mediaArchiver).setLocation(((PageArchiver)pageArchiver).getLocation()+File.separator+"images");
		
		while (!urlQueue.isEmpty()) {

			String url = urlQueue.pollFirst();
			try {
				if (!visitedUrls.contains(url)) {
					visitedUrls.add(url);
					Document doc = Jsoup.connect(url).timeout(10 * 1000).get();
					Elements anchors = doc.select("a[href]");
					Iterator<Element> aItr = anchors.iterator();

					while (aItr.hasNext()) {
						Element urlElement = aItr.next();
						String aUrl = urlElement.attr("abs:href");
						String fileName = fileNameHandler.mapUrlToFile(aUrl);
						urlElement.attr("href", fileName);
						urlQueue.addLast(aUrl);
					}

					Elements links = doc.select("link[href]");
					Elements medias = doc.select("img[src]");
					Iterator<Element> lItr = links.iterator();
					Iterator<Element> mItr = medias.iterator();

					while (lItr.hasNext()) {
						Element urlElement = lItr.next();
						String lUrl = urlElement.attr("abs:href");
						urlElement.attr("href", lUrl);
					}

					while (mItr.hasNext()) {
						Element urlElement = mItr.next();
						String mUrl = urlElement.attr("abs:src");
						if (urlElement.tagName().equals("img")) {
							String mediaName = mediaFileNameHandler
									.mapUrlToFile(mUrl);
							urlElement.attr("src", "images"+File.separator+ mediaName);
							mediaArchiver.archive(mUrl);
						} else {
							urlElement.attr("src", mUrl);
						}
					}
					
					pageArchiver.archive(doc, url);
				}
			} catch (Exception e) {
				System.out.println("Problem occured while opening the url "
						+ url + " Message " + e.getMessage());
				//e.printStackTrace();
			}
		}
		return visitedUrls.size();
	}

	
	@Autowired
	public void setFileNameHandler(FileNameHandler fileNameHandler) {
		this.fileNameHandler = fileNameHandler;
	}

	@Autowired
	public void setMediaFileNameHandler(MediaFileNameHandler mediaFileNameHandler) {
		this.mediaFileNameHandler = mediaFileNameHandler;
	}


	@Autowired
	public void setPageArchiver(Archiver pageArchiver) {
		this.pageArchiver = pageArchiver;
	}

    @Autowired
	public void setMediaArchiver(Archiver mediaArchiver) {
		this.mediaArchiver = mediaArchiver;
	}
	
}
