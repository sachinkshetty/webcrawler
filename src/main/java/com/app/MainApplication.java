package com.app;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.service.WebCrawlerService;
import com.google.gson.JsonObject;

@SpringBootApplication
public class MainApplication implements CommandLineRunner{
	
	@Resource
	private WebCrawlerService webcrawlerService;

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		JsonObject jsonOBject = webcrawlerService.crawl();
		System.out.println(jsonOBject);
	}
}
