package com.pramati.handler;

import java.util.ArrayDeque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pramati.urlvisitor.UrlVisitor;

public class BaseUrlHandler {

	@Autowired
	UrlVisitor anchorVisitor;

	public void startCrawling(String baseUrl) {
		ArrayDeque<String> urlQueue = new ArrayDeque<String>();
		urlQueue.add(baseUrl);
		int visitCount = anchorVisitor.visit(urlQueue);
		System.out.println("Number of visited page "+visitCount);
	}

	public static void main(String[] args) {
		if (args.length == 1) {
			String baseUrl = args[0];
				ApplicationContext ctx = new ClassPathXmlApplicationContext(
						"beans.xml");
				BaseUrlHandler crawler = (BaseUrlHandler) ctx
						.getBean("baseUrlHandler");
				crawler.startCrawling(baseUrl);
		} else {
			System.out.println("Please provide the base URL");
		}
	}
}
