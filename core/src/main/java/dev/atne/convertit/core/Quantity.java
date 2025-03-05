package dev.atne.convertit.core;

import java.math.BigDecimal;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class Quantity {

	private static final String[] baseUnits = {
		"time",
		"length",
		"mass",
		"current",
		"temperature",
		"amount",
		"luminosity",
	};

	// Map of all the alternative names to the base name
	public static final Map<String, String> unitNames = new HashMap<String, String>();

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
				String scaler = u + "_scaler";
				String vector = u + "_vector";
				if (!obj.has(scaler)) { continue; }
				if (!obj.has(vector)) { continue; }
				
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
	private final BigDecimal   value; // A scaler multiple representing the "number" of units
	private final BigDecimal[] scalers; // Define the offset multiple from the base unit
	private final BigDecimal[] vectors; // Define the dimension of the unit

	private static BigDecimal pow(BigDecimal a, BigDecimal b) {
		return a.pow(b.intValue());
	}

	public static Quantity Convert(Quantity a, Quantity b) {
		BigDecimal value = a.value;

		for (int i = 0; i < baseUnits.length; i++) {
			if (a.scalers[i] == null || a.vectors[i] == null || b.scalers[i] == null || b.vectors[i] == null) {
				continue;
			}

			BigDecimal factor = pow(a.scalers[i].divide(b.scalers[i]), b.vectors[i]);
			System.out.println(value + " " + factor);
			value = value.multiply(factor);
		}

		return new Quantity(value, b);
	}

	public Quantity(String definition) {
		this.definition = definition;
		this.scalers = new BigDecimal[baseUnits.length];
		this.vectors = new BigDecimal[baseUnits.length];
		String unitBoundries = "[ \\/\\*]";
		String[] u = this.definition.split(unitBoundries);
		value = new BigDecimal(u[0]);

		for (int i = 1; i < u.length; i++) {
			String properName = unitNames.get(u[i]);
			for (int k = 0; k < baseUnits.length; k++) {
				String base = baseUnits[k];
				
				HashMap<String, BigDecimal> scalerMap = unitScalers.getOrDefault(base, null);
				HashMap<String, BigDecimal> vectorMap = unitVectors.getOrDefault(base, null);
				assert scalerMap != null : "Base unit " + base + "  doesn't exist";
				assert vectorMap != null : "Base unit " + base + "  doesn't exist";


				this.scalers[k] = scalerMap.getOrDefault(properName, null);
				this.vectors[k] = vectorMap.getOrDefault(properName, null);
			}
		}
	}

	public Quantity(BigDecimal value, Quantity unit) {
		this((value.toString() + " " + unit.definition.split(" ")[1]));
	}

	public Boolean isEqual(Quantity that) {
		if (this.definition.length() != that.definition.length()) {
			return false;
		}

		for (int i = 0; i < this.definition.length(); i++) {
			if (this.definition.charAt(i) != that.definition.charAt(i)) {
				return false;
			}
		}

		return true;
	}

	public BigDecimal getValue() {
		return this.value;
	}

	public String toString() {
		return this.definition;
	}
}
