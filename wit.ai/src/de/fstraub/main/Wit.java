package de.fstraub.main;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;

import javax.net.ssl.HttpsURLConnection;

public class Wit {

	private String authID;

	public Wit(String authID) {
		this.authID = authID;
	}

	public String sendTextRequest(String request) {
	    if(request.length() < 0 || request.length() > 255)
	        return "Your query is invalid. Length must be > 0 and < 256";

		URL url = null;
		String response = null;
		try {
			url = new URL("https://api.wit.ai/message?v=12.11.2017&q=" + URLEncoder.encode(request, "UTF-8"));
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			authorize(connection);
			connection.connect();

			response = getResponse(connection);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response;
	}
	public String sendSpeechRequest(File waveFile) {
		URL url;
		String response = null;
		try {
			url = new URL("https://api.wit.ai/speech");

			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setUseCaches(false);
			connection.setDoOutput(true); // indicates POST method
			connection.setDoInput(true);

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Cache-Control", "no-cache");
			authorize(connection);
			connection.setRequestProperty("Content-Type", "audio/wav");
			;
			byte[] bytes = Files.readAllBytes(waveFile.toPath());
			DataOutputStream request = new DataOutputStream(connection.getOutputStream());
			request.write(bytes);
			request.flush();
			request.close();

			response = getResponse(connection);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	private void authorize(HttpURLConnection connection) {
		connection.setRequestProperty("Authorization", String.format("Bearer %s", authID));
	}

	private String getResponse(HttpsURLConnection connection) throws IOException {
		String ret = null;
		int status = connection.getResponseCode();
		if (status == HttpsURLConnection.HTTP_OK) {
			InputStream responseStream = new BufferedInputStream(connection.getInputStream());
			ret = Util.streamToString(responseStream);
			connection.disconnect();
			return ret;
		} else if(status == HttpsURLConnection.HTTP_BAD_REQUEST){
			return "Wit.ai could not interpret your input!";
		}else{
			throw new IOException("Server returned non-OK status: " + status);
		}
	}
}
