package com.browserstack.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.browserstack.base.TestBase;

public class TranslationAPI extends TestBase {

	public static String getTranslation(String text, String fromLanguageCode, String toLanguageCode) throws Exception {
		// API URL
		String apiUrl = prop.getProperty("apiUrl");

		// Prepare the JSON body
		JSONObject requestBody = new JSONObject();
		requestBody.put("from", fromLanguageCode); // Spanish "es"
		requestBody.put("to", toLanguageCode); // English "en"
		requestBody.put("q", text);

		// Make the HTTP request
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		try {
			// Set up the connection
			URL url = new URL(apiUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("x-rapidapi-host", "rapid-translate-multi-traduction.p.rapidapi.com");
			connection.setRequestProperty("x-rapidapi-key", prop.getProperty("rapidapiKey"));
			connection.setDoOutput(true);

			// Send the request body
			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = requestBody.toString().getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			// Get the HTTP status code
			int statusCode = connection.getResponseCode();
			Assert.assertEquals(statusCode, HttpURLConnection.HTTP_OK, "Failed to get valid response from API. Status code: " + statusCode);

			// Read the response
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}

			// Print the raw response for debugging
			//System.out.println("API Response: " + response.toString());

			// Handle non-JSON responses
			if (statusCode != HttpURLConnection.HTTP_OK) {
				BufferedReader errorReader = new BufferedReader(
						new InputStreamReader(connection.getErrorStream(), "utf-8"));
				StringBuilder errorResponse = new StringBuilder();
				while ((line = errorReader.readLine()) != null) {
					errorResponse.append(line);
				}
				System.out.println("Error Response: " + errorResponse.toString());
				throw new Exception("Failed to get translation: HTTP " + statusCode);
			}

			// Parse the response as a JSON array
			try {
				JSONArray responseArray = new JSONArray(response.toString());
				// Get the first element of the array which is the translated text
				return responseArray.getString(0); // The translated text is in the first position of the array
			} catch (Exception e) {
				// Handle case where the response is not valid JSON
				throw new Exception("Failed to parse translation response: " + e.getMessage());
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed to get translation: " + e.getMessage());
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

}
