package com.app.service.impl;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.app.service.HTMLParserService;

@Service
public class HTMLParserServiceImpl implements HTMLParserService{
	
	/**
	 * Method to get the parser document from the url 
	 */
	
	@Override
	public Document getDocument(String link) {
		URL url;
		Document docParser = null;
		try {
			url = new URL(link);
			docParser = Jsoup.parse(url, 100000);
		} catch (IOException e) {			
			System.out.println("Exception While getting the document for parsing"+e);
		}
		return docParser;
	}
	
	/**
	 * Method to get elements from the document using a css selector
	 */
	@Override
	public Elements getElementsFromDocument(Document document, String cssSelector) {
		Elements elements = document.select(cssSelector);
		return elements;		
	}
	
	/**
	 * Method to get element using tag name
	 */
	@Override
	public Element getElementsByTag(Element element,String tag){
		return element.getElementsByTag(tag).first();		
	}
	
	/**
	 * Method to get the the text of the child element from the sibling elements
	 */
	@Override
	public String getDescriptionOfProduct(Element element){
		Element siblingElement = element.nextElementSibling();	
		String detail = siblingElement.children().get(0).text();					
		return detail;
	}
	

}
