package ca.docilecraft.fetchers;

import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UUIDFetcher {
	private static final String PROFILE_URL = "https://api.mojang.com/users/profiles/minecraft/";
	private final static JSONParser jsonParser = new JSONParser();
	
	public static UUID getUUIDOf(String name) throws Exception {
		HttpURLConnection connection = (HttpURLConnection) new URL(PROFILE_URL+name).openConnection();
		BufferedInputStream input = new BufferedInputStream(connection.getInputStream());
		
		input.mark(1);
		if(input.read() == -1)
			return null;
		input.reset();
		
		InputStreamReader inputReader = new InputStreamReader(input);
		JSONObject response = (JSONObject) jsonParser.parse(inputReader);
		String id = (String) response.get("id");
		
		return getUUID(id);
	}
	
	private static UUID getUUID(String id) {
		return UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" +id.substring(20, 32));
	}
}
