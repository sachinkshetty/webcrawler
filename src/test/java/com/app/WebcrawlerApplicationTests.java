package com.app;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.service.HTMLParserService;
import com.app.service.WebCrawlerService;
import com.google.gson.JsonObject;



@RunWith(SpringRunner.class)
@SpringBootTest
public class WebcrawlerApplicationTests {
	
	@Resource
	private HTMLParserService htmlParserservice;
	
	@Resource
	private WebCrawlerService webCrawlerService;
	
	@Value("${crawler.url}")
	private String url;
	
	@Before
	public void setup(){		
             
	}
	

	@Test
	public void testJsonResultSize() {
		JsonObject jsonObj = webCrawlerService.crawl();
		Assert.assertNotNull(jsonObj);
		Assert.assertEquals(2, jsonObj.size());
		Assert.assertEquals(7, jsonObj.getAsJsonArray("results").size());		
		
	}
	
	@Test
	public void testJsonResultDataForTitle() {
		JsonObject jsonObj = webCrawlerService.crawl();
		Assert.assertNotNull(jsonObj);
		JsonObject jsObj1 = jsonObj.getAsJsonArray("results").get(0).getAsJsonObject();			
		String title1 = "Sainsbury's Apricot Ripe & Ready x5";
		Assert.assertEquals(title1, jsObj1.get("title").getAsString());
		
		JsonObject jsObj2 = jsonObj.getAsJsonArray("results").get(6).getAsJsonObject();			
		String title2 = "Sainsbury's Kiwi Fruit, Ripe & Ready x4";
		Assert.assertEquals(title2, jsObj2.get("title").getAsString());
		
	}
	
	@Test
	public void testJsonResultDataForDescription() {
		JsonObject jsonObj = webCrawlerService.crawl();
		Assert.assertNotNull(jsonObj);
		JsonObject jsObj1 = jsonObj.getAsJsonArray("results").get(4).getAsJsonObject();			
		String description1 = "Conference";
		Assert.assertEquals(description1, jsObj1.get("description").getAsString());
		
		JsonObject jsObj2 = jsonObj.getAsJsonArray("results").get(3).getAsJsonObject();			
		String description2 = "Avocados";
		Assert.assertEquals(description2, jsObj2.get("description").getAsString());
	}
}
