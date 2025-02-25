package dev.atne.convertit.core;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Convert {
	private static final MathContext context = new MathContext(8, RoundingMode.HALF_UP);


	public static BigDecimal convert(String rawOrigin, String rawConvert, BigDecimal count) {
		String origin = Quantity.unitNames.getOrDefault(rawOrigin, "null");
		if (origin == "null") {
			System.out.println("Invalid origin unit \"" + rawOrigin + "\"");
			System.exit(1);
		}

		String convert = Quantity.unitNames.getOrDefault(rawConvert, "null");
		if (convert == "null") {
			System.out.println("Invalid convert unit \"" + rawConvert + "\"");
			System.exit(1);
		}

		BigDecimal factor = Quantity.unitScalers.get("length").get(origin).divide(Quantity.unitScalers.get("length").get(convert), context);
		BigDecimal result = count.multiply(factor);
		return result;
	}
}
