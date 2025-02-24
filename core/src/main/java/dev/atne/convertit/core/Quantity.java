package dev.atne.convertit.core;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;
import java.io.InputStream;

public final class Quantity {
	static {
		// Parse the json resources into maps
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("units/foot.json");
		Scanner s = new Scanner(is).useDelimiter("\\A");
		String result = s.hasNext() ? s.next() : "";
		s.close();
		
	}

	public static final Map<String, String> unitNames = Map.ofEntries(
		Map.entry("feet",				"foot"),
		Map.entry("meters",			"meter")
	);

	// Map of all length conversion to meters
	public static final Map<String, BigDecimal> conversionFactors = Map.ofEntries(
		Map.entry("meter",		new BigDecimal("1.0")),
		Map.entry("foot",			new BigDecimal("0.3048"))
	);

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
