package com.app.service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public interface HTMLParserService {

	public Document getDocument(String link);

	Elements getElementsFromDocument(Document document, String cssSelector);

	Element getElementsByTag(Element element, String tag);

	String getDescriptionOfProduct(Element element);
}
