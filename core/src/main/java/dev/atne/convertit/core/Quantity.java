package dev.atne.convertit.core;

import java.math.BigDecimal;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public final class Quantity {
	// Map of all the alternative names to the base name
	public static final Map<String, String> unitNames = new HashMap<String, String>();

	// Map of all length conversion to meters
	public static final Map<String, BigDecimal> conversionFactors = new HashMap<String, BigDecimal>();

	static {
		JSONArray unitsIndex = loadJSONResource("units_index").getJSONArray("index");

		for (int i = 0; i < unitsIndex.length(); i++) {
			JSONObject obj = loadJSONResource(unitsIndex.getString(i));
			
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

	private static final JSONObject loadJSONResource(String name) {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream unitsIndexSource = classloader.getResourceAsStream("units/" + name + ".json");
		Scanner scanner = new Scanner(unitsIndexSource).useDelimiter("\\A");
		String result = scanner.hasNext() ? scanner.next() : "";
		scanner.close();

		return new JSONObject(result);
	}

	// Indicies into the arrays
	private final short TIME 			= 0;
	private final short LENGTH 		= 1;
	private final short MASS 			= 2;
	private final short CURRENT 		= 3;
	private final short TEMPERATURE 	= 4;
	private final short AMOUNT 		= 5;
	private final short LUMINOSITY 	= 6;
	private final short UNIT_COUNT	= 7; // SHOULD ALWAYS BE THE LAST INDEX ++;

	// Every quantity has a value and a unit
	// Every unit is made up of smaller units, raised to a power
	private final BigDecimal[] scaler = new BigDecimal[UNIT_COUNT];
	private final BigDecimal[] vector = new BigDecimal[UNIT_COUNT];

	public Quantity(String definition) {
	}
}
