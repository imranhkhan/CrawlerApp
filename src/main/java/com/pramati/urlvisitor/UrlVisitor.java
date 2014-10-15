package com.pramati.urlvisitor;

import java.util.ArrayDeque;

public interface UrlVisitor {
	public int visit(ArrayDeque<String> urlQueue);
}
