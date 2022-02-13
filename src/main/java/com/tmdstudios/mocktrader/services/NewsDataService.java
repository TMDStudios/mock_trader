package com.tmdstudios.mocktrader.services;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.tmdstudios.mocktrader.models.News;

public class NewsDataService {
	
	private static String NEWS_DATA = "https://raw.githubusercontent.com/TMDStudios/json_files/main/random_news.json";
	
	public List<News> fetchNews() throws IOException, InterruptedException {
		List<News> allNews = new ArrayList<>();
		
		StringBuilder jsonData = new StringBuilder();
		
		URL url = new URL(NEWS_DATA);
		
		// open a connection to this URL
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		try {
			// read in the bytes
			InputStream inputStream = urlConnection.getInputStream();
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
			
			// read them as characters.
			InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			// read one line at a time.
			String inputLine = bufferedReader.readLine();
			while (inputLine != null) {
				// add this to our output
				jsonData.append(inputLine);
				// reading the next line
				inputLine = bufferedReader.readLine();
			}
			JSONArray root = new JSONArray(jsonData.toString());
			for(int i = 0; i<root.length(); i++) {
				String source = root.getJSONObject(i).getString("source");
				String news = root.getJSONObject(i).getString("news");
				Double effect = root.getJSONObject(i).getDouble("effect");
				News story = new News(source, news, effect);
				allNews.add(story);
			}
		} finally {
			urlConnection.disconnect();
		}
		return allNews;
	}

}
