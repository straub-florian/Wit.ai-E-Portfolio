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
		URL url = null;
		String response = null;
		try {
			url = new URL("https://api.wit.ai/message?v=12.11.2017&q=" + URLEncoder.encode(request, "UTF-8"));
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			authorize(connection);
			connection.connect();

			response = "";
			// checks server's status code first
			int status = connection.getResponseCode();
			if (status == HttpURLConnection.HTTP_OK) {
				InputStream responseStream = new BufferedInputStream(connection.getInputStream());
				response = Util.streamToString(responseStream);
				connection.disconnect();
			} else {
				throw new IOException("Server returned non-OK status: " + status);
			}
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

			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setUseCaches(false);
			httpConn.setDoOutput(true); // indicates POST method
			httpConn.setDoInput(true);

			httpConn.setRequestMethod("POST");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("Cache-Control", "no-cache");
			authorize(httpConn);
			httpConn.setRequestProperty("Content-Type", "audio/wav");
			;
			byte[] bytes = Files.readAllBytes(waveFile.toPath());
			DataOutputStream request = new DataOutputStream(httpConn.getOutputStream());
			request.write(bytes);
			request.flush();
			request.close();

			response = "";
			// checks server's status code first
			int status = httpConn.getResponseCode();
			if (status == HttpURLConnection.HTTP_OK) {
				InputStream responseStream = new BufferedInputStream(httpConn.getInputStream());
				response = Util.streamToString(responseStream);
				httpConn.disconnect();
			} else {
				throw new IOException("Server returned non-OK status: " + status);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	private void authorize(HttpURLConnection connection) {
		connection.setRequestProperty("Authorization", String.format("Bearer %s", authID));
	}
}
