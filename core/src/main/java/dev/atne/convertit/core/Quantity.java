package dev.atne.convertit.core;

import java.math.BigDecimal;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public final class Quantity {
	// Map of all the alternative names to the base name
	public static final Map<String, String> unitNames = new HashMap<String, String>();

	private static final String[] baseUnits = {
		"time",
		"length",
		"mass",
		"current",
		"temperature",
		"amount",
		"luminosity",
	};

	// The twin evils (These are the same, they just have different names)
	public static final HashMap<String, HashMap<String, BigDecimal>> unitScalers = 
		new HashMap<String, HashMap<String, BigDecimal>>();
	public static final HashMap<String, HashMap<String, BigDecimal>> unitVectors = 
		new HashMap<String, HashMap<String, BigDecimal>>();

	static {
		for (String u : baseUnits) {
			unitScalers.put(u, new HashMap<String, BigDecimal>());
			unitVectors.put(u, new HashMap<String, BigDecimal>());
		}
		
		JSONArray unitsIndex = loadJSONResource("units_index").getJSONArray("index");

		for (int i = 0; i < unitsIndex.length(); i++) {
			JSONObject obj = loadJSONResource(unitsIndex.getString(i));
			
			String name = obj.getString("name");
			JSONArray alternative_names = obj.getJSONArray("alternative_names");
			unitNames.put(name, name); 
			for (int k = 0; k < alternative_names.length(); k++) {
				unitNames.put(alternative_names.getString(k), name); 
			}

			for (String u : baseUnits) {
				final String scaler = u + "_scaler";
				final String vector = u + "_vector";
				if (!obj.has(scaler)) { continue; }
				
				unitScalers.get(u).put(name, obj.getBigDecimal(scaler));
				unitVectors.get(u).put(name, obj.getBigDecimal(vector));
			}
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

	private final String definition;
	// Every quantity has a value and a unit
	// Every unit is made up of smaller units, raised to a power
	private final BigDecimal   scaler = new BigDecimal("0");
	private final BigDecimal[] vector = new BigDecimal[baseUnits.length];

	public Quantity(String definition) {
		this.definition = definition;
	}
}
