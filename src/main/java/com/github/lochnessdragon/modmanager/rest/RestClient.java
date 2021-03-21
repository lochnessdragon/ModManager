package com.github.lochnessdragon.modmanager.rest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonObject;

import net.minecraft.util.JsonHelper;

public class RestClient {

	String address;
	
	public RestClient(String address) {
		this.address = address;
	}
	
	public JsonObject request(String endpoint) {
		String result = "{}";
		
		try {
			URL url = new URL(address + endpoint);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			connection.setRequestMethod("GET");
			
			connection.connect();
			
			// To store our response
			StringBuilder content;

			// Get the input stream of the connection
			try (BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			    String line;
			    content = new StringBuilder();
			    while ((line = input.readLine()) != null) {
			        // Append each line of the response and separate them
			        content.append(line);
			        content.append(System.lineSeparator());
			    }
			} finally {
			    connection.disconnect();
			}
			
			result = content.toString();
		} catch (FileNotFoundException e) {
			//logger.warn("Could not locate endpoint: {}", endpoint);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return JsonHelper.deserialize(result);
	}
	
}
