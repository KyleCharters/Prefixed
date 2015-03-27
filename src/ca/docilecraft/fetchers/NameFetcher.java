package ca.docilecraft.fetchers;

import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class NameFetcher{
	private static final String PROFILE_URL = "https://api.mojang.com/user/profiles/";
	private static final String PROFILE_URL_END = "/names";
	private static final JSONParser jsonParser = new JSONParser();

	public static String getUsernameOf(UUID uuid) throws Exception{
		HttpURLConnection connection = (HttpURLConnection) new URL(PROFILE_URL+uuid.toString().replace("-", "")+PROFILE_URL_END).openConnection();
		BufferedInputStream input = new BufferedInputStream(connection.getInputStream());
		
		input.mark(1);
		if(input.read() == -1)
			return null;
		input.reset();
		
		InputStreamReader inputReader = new InputStreamReader(input);
		JSONArray response = (JSONArray) jsonParser.parse(inputReader);
		JSONObject latest = (JSONObject) response.get(response.size()-1);
		String name = (String) latest.get("name");
		
		return name;
	}
}
