package com.app.service.impl;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.app.service.HTMLParserService;
import com.app.service.WebCrawlerService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;



@Service
public class WebCrawlerServiceImpl implements WebCrawlerService {
	
	@Value("${crawler.url}")
	private String url;
	
	@Value("${css.firstelement}")
	private String firstelement;
	
	@Value("${css.price}")
	private String price;
	
	@Value("${css.productDescription}")
	private String productDescription;
	
	@Resource
	private HTMLParserService htmlParserService;
	
	private double total =0;

	/**
	 * Method to process the url and get the data in json format
	 */
	@Override
	public JsonObject crawl() {
		double sum=0;
		JsonArray jsonArray = new JsonArray();
		Element firstElement = processHtml(url,firstelement).first();		
		Elements liElements = firstElement.children();
		
		for (Element lielement:liElements) {
			JsonObject jsonObject = new JsonObject();
			Element aElement =  htmlParserService.getElementsByTag(lielement, "a");
			String title = aElement.text();
			String pdpLink = aElement.attr("href");		
			jsonObject.addProperty("title", title);
			
			String unitPrice = matchNumbers(lielement.select(price).first().text());
			sum = Double.parseDouble(unitPrice);
			total = total+sum;			
			jsonObject.addProperty("unit_price", unitPrice);
			
			Elements productDetailsElements = processHtml(pdpLink,productDescription);					
			jsonObject = getPDPDetails(productDetailsElements,jsonObject);			
			jsonArray.add(jsonObject);		
		}
		JsonObject result = getJsonData(jsonArray, total);
		return result;
	}
	
	/**
	 * Method to get the outer json format
	 */
	@Override
	public JsonObject getJsonData(JsonArray jsonArray,double sumOfPrice){
		JsonObject outerJson = new JsonObject();
		outerJson.add("results", jsonArray);
		outerJson.addProperty("sum", new DecimalFormat("#.##").format(sumOfPrice));
		return outerJson;
	}
	
	/**
	 * Method to get the elements using url and css selector
	 * @param url
	 * @param selector
	 * @return
	 */
	private Elements processHtml(String url,String selector){
		Document htmlDocument = htmlParserService.getDocument(url);
		Elements elements = htmlParserService.getElementsFromDocument(htmlDocument, selector);
		return elements;
	}
	
	/**
	 * Method to get the PDP details using the elements and populate it in json object
	 * @param elements
	 * @param jsonObj
	 * @return
	 */
	private JsonObject getPDPDetails(Elements elements,JsonObject jsonObj){		
		for (Element prodDtlEl:elements) {	
			if (prodDtlEl.text().equalsIgnoreCase("description")) {
				String description = htmlParserService.getDescriptionOfProduct(prodDtlEl);
				jsonObj.addProperty("description", description);
			}
			else if (prodDtlEl.text().equalsIgnoreCase("size")) {
				String size = htmlParserService.getDescriptionOfProduct(prodDtlEl);
				jsonObj.addProperty("size", size);
			}	
		}
		
		return jsonObj;		
	}
	
	
	/**
	 * Regex to get the price from the string
	 * @param line
	 * @return
	 */
	private String matchNumbers(String line){
		Pattern p = Pattern.compile("\\d+(\\.\\d+)?");
		Matcher m = p.matcher(line); 
		while (m.find()) {
			 return m.group();
		}
		return "";
	}
}
