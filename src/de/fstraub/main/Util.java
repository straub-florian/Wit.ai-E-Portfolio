package de.fstraub.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Util {

	public static String streamToString(InputStream stream) {
		BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(stream));

		String line = "";
		StringBuilder stringBuilder = new StringBuilder();

		try {
			while ((line = responseStreamReader.readLine()) != null) {
				stringBuilder.append(line).append("\n");
			}
			responseStreamReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}

}
