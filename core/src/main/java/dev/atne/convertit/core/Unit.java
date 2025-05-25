package dev.atne.convertit.core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.math.BigDecimal;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Unit {
	// Static
	private static final String[] baseUnits = {
		"time",
		"length",
		// "mass",
		// "current",
		// "temperature",
		// "amount",
		// "luminosity",
	};
	public static final int DIMENSIONS = baseUnits.length;

// Map of all the alternative names to the base name
	public static final Map<String, String> unitNames = new HashMap<String, String>();

	// The twin evils (These are the same, they just have different names)
	public static final HashMap<String, HashMap<String, BigDecimal>> unitScalers = 
		new HashMap<String, HashMap<String, BigDecimal>>();
	public static final HashMap<String, HashMap<String, BigDecimal>> unitVectors = 
		new HashMap<String, HashMap<String, BigDecimal>>();

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

			unitScalers.put(name, new HashMap<String, BigDecimal>());
			unitVectors.put(name, new HashMap<String, BigDecimal>());
			for (String u : baseUnits) {
				String scaler = u + "_scaler";
				String vector = u + "_vector";
				if (!obj.has(scaler)) { continue; }
				if (!obj.has(vector)) { continue; }
				
				unitScalers.get(name).put(u, obj.getBigDecimal(scaler));
				unitVectors.get(name).put(u, obj.getBigDecimal(vector));
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

	public static Boolean IsEqual(Unit a, Unit b) {
		assert (a.vectors.length == b.vectors.length);
		assert (a.scalers.length == b.scalers.length);

		for (int i = 0; i < a.vectors.length; i++) {
			if (a.vectors[i].compareTo(b.vectors[i]) != 0)  {
				return false;
			}
			if (a.scalers[i].compareTo(b.scalers[i]) != 0)  {
				return false;
			}
		}

		return true;
	}

	public static Boolean IsEqualDimensions(Unit a, Unit b) {
		assert (a.vectors.length == b.vectors.length);
		assert (a.scalers.length == b.scalers.length);

		for (int i = 0; i < a.vectors.length; i++) {
			if (a.vectors[i].compareTo(b.vectors[i]) != 0)  {
				return false;
			}
		}

		return true;
	}

	// Non-static
	private final BigDecimal[] scalers;
	private final BigDecimal[] vectors;

	public Unit(String constructor) {
		this.scalers = new BigDecimal[DIMENSIONS];
		this.vectors = new BigDecimal[DIMENSIONS];

		String name = unitNames.get(constructor);
		HashMap<String, BigDecimal> scaler = unitScalers.get(name);
		HashMap<String, BigDecimal> vector = unitVectors.get(name);
		for (int i = 0; i < DIMENSIONS; i++) {
			this.scalers[i] = scaler.get(baseUnits[i]);
			if (this.scalers[i] == null) { this.scalers[i] = BigDecimal.ZERO; }
			this.vectors[i] = vector.get(baseUnits[i]);
			if (this.vectors[i] == null) { this.vectors[i] = BigDecimal.ZERO; }
		}
	}

	public Unit(BigDecimal[] scalers, BigDecimal[] vectors) {
		assert (scalers.length == vectors.length);
		assert (vectors.length == baseUnits.length);

		this.scalers = scalers.clone();
		this.vectors = vectors.clone();
	}

	public BigDecimal[] getScalers() {
		return scalers.clone();
	}

	public BigDecimal[] getVectors() {
		return vectors.clone();
	}
}
