package org.json;


public class ParJSON {
	public static String jsonparse(String jsonData) throws Exception
	{
		String url_short="";
		JSONObject demoJson =  new JSONObject(jsonData);
		JSONArray urlsList = demoJson.getJSONArray("urls");
		 
		for ( int i=0; i<urlsList.length(); i++){
			
			JSONObject deam = new JSONObject(urlsList.getString(i));

			url_short = deam.getString("url_short");
		}
		return url_short;
	}
}
