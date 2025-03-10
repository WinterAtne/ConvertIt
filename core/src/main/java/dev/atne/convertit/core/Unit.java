package dev.atne.convertit.core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.math.BigDecimal;

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

	public static Boolean IsEqualDimension(Unit a, Unit b) {
		assert (a.vectors.length == b.vectors.length);

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
		return scalers.clone();
	}
}
