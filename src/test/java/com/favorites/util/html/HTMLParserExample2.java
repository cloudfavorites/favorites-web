package com.favorites.util.html;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParserExample2 {

  public static void main(String[] args) {

	Document doc;
	try {

		//get all images
		doc = Jsoup.connect("http://yahoo.com").get();
		Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
		for (Element image : images) {

			System.out.println("\nsrc : " + image.attr("src"));
			System.out.println("height : " + image.attr("height"));
			System.out.println("width : " + image.attr("width"));
			System.out.println("alt : " + image.attr("alt"));

		}

	} catch (IOException e) {
		e.printStackTrace();
	}

  }

}