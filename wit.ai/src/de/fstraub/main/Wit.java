package de.fstraub.main;

import org.json.JSONObject;

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

	public WitResponse sendTextRequest(String request) {
        if (request.length() < 0 || request.length() > 255) {
            System.err.println("Your query is invalid. Length must be > 0 and < 256");
            return null;
        }

		URL url = null; // https://api.wit.ai/message?v=12.11.2017&q=
        JSONObject response = null;

        //TODO: Connect to wit.ai, send a text request and handle the response

		return new WitResponse(response);
	}

	public WitResponse sendSpeechRequest(File waveFile) {
		URL url = null; // https://api.wit.ai/speech
		JSONObject response = null;

        //TODO: Connect to wit.ai, send a speech request and handle the response

		return  new WitResponse(response);
	}
}
