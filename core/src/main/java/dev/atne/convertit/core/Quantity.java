package dev.atne.convertit.core;

import java.math.BigDecimal;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public final class Quantity {
	public static final Map<String, String> unitNames = new HashMap<String, String>();

	// Map of all length conversion to meters
	public static final Map<String, BigDecimal> conversionFactors = new HashMap<String, BigDecimal>();

	private static final JSONObject loadJSONResource(String name) {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream unitsIndexSource = classloader.getResourceAsStream(name + ".json");
		Scanner scanner = new Scanner(unitsIndexSource).useDelimiter("\\A");
		String result = scanner.hasNext() ? scanner.next() : "";
		scanner.close();

		return new JSONObject(result);
	}

	static {
		JSONArray unitsIndex = loadJSONResource("units/units_index").getJSONArray("index");

		for (int i = 0; i < unitsIndex.length(); i++) {
			JSONObject obj = loadJSONResource("units/" + unitsIndex.getString(i));
			
			String name = obj.getString("name");
			JSONArray alternative_names = obj.getJSONArray("alternative_names");
			unitNames.put(name, name); 
			for (int k = 0; k < alternative_names.length(); k++) {
				unitNames.put(alternative_names.getString(k), name); 
			}

			BigDecimal factor = obj.getBigDecimal("length_scaler");
			conversionFactors.put(name, factor);
		}
	}


	// Indicies into the arrays
	private final short time 			= 0;
	private final short length 		= 1;
	private final short mass 			= 2;
	private final short current 		= 3;
	private final short temperature 	= 4;
	private final short amount 		= 5;
	private final short luminosity 	= 6;

	// Every quantity has a value and a unit
	// Every unit is made up of smaller units, raised to a power
	private final BigDecimal   scaler = new BigDecimal("0");
	private final BigDecimal[] vector = new BigDecimal[7];

	public Quantity(String definition) {
	}

	public BigDecimal getValue() {
		return scaler;
	}

}
