package finished_eportfolio.straub.main;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;

public class Wit {

	private String authID;

	public Wit(String authID) {
		this.authID = authID;
	}

	public WitResponse sendTextRequest(String request) {
        if (request.length() < 0 || request.length() > 255) {
            System.err.println("Your query is invalid. Length must be > 0 and < 256");
            return null;
        }

		URL url = null; //
        JSONObject response = null;

		try {
			url = new URL("https://api.wit.ai/message?v=12.11.2017&q=" + URLEncoder.encode(request, "UTF-8"));
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

			connection.setRequestMethod("GET");
			authorize(connection);
			connection.connect();
            response = getResponse(connection);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

        //TODO: Connect to wit.ai, send a text request and handle the response

		return new WitResponse(response);
	}

	private void authorize(HttpsURLConnection connection) {
		connection.setRequestProperty("Authorization", String.format("Bearer %s", authID));
	}

	public WitResponse sendSpeechRequest(File waveFile) {
		URL url = null; // https://api.wit.ai/speech
		JSONObject response = null;

        //TODO: Connect to wit.ai, send a speech request and handle the response
        try {
            url = new URL("https://api.wit.ai/speech");

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Cache-Control", "no-caches");
            authorize(connection);
            connection.setRequestProperty("Content-Type", "audio/wav");

            byte[] bytes = Files.readAllBytes(waveFile.toPath());
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(bytes);
            out.flush();
            out.close();

            response = getResponse(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new WitResponse(response);
	}

	private JSONObject getResponse(HttpsURLConnection connection){
        int status = 0;
        String returnstring;
        try {
            status = connection.getResponseCode();
            if(status == HttpsURLConnection.HTTP_OK) {
                InputStream responseStream = new BufferedInputStream(connection.getInputStream());
                returnstring = Util.streamToString(responseStream);
                return new JSONObject(returnstring);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
