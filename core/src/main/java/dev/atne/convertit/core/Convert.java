package dev.atne.convertit.core;

import java.util.Map;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Convert {
	private static final MathContext context = new MathContext(8, RoundingMode.HALF_UP);

	public enum Unit {
		NULL,
		// Imperial Length
		FOOT,
		INCH,
		// Metric Length
		METER,

		// Time
		SECOND,
		MINUTE,
		HOUR,
	}

	private static final Map<String, Unit> unitNames = Map.ofEntries(
		Map.entry("feet",				Unit.FOOT),
		Map.entry("inches",			Unit.INCH),
		Map.entry("meters",			Unit.METER),

		Map.entry("second",				Unit.SECOND),
		Map.entry("minute",				Unit.MINUTE),
		Map.entry("hour",					Unit.HOUR)
	);

	// Map of all length conversion to meters
	private static final Map<Unit, BigDecimal> conversionFactors = Map.ofEntries(
		Map.entry(Unit.METER,		new BigDecimal("1.0")),
		Map.entry(Unit.FOOT,			new BigDecimal("0.3048")),
		Map.entry(Unit.INCH,			new BigDecimal("0.0254")),

		Map.entry(Unit.HOUR,			new BigDecimal("1.0")),
		Map.entry(Unit.MINUTE,		BigDecimal.ONE.divide(new BigDecimal(60), context)), // Impresise unit
		Map.entry(Unit.SECOND,		BigDecimal.ONE.divide(new BigDecimal(3600), context))
	);


	public static BigDecimal convert(String rawOrigin, String rawConvert, BigDecimal count) {
		Unit origin = unitNames.getOrDefault(rawOrigin, Unit.NULL);
		if (origin == Unit.NULL) {
			System.out.println("Invalid origin unit \"" + rawOrigin + "\"");
			System.exit(1);
		}

		Unit convert = unitNames.getOrDefault(rawConvert, Unit.NULL);
		if (convert == Unit.NULL) {
			System.out.println("Invalid convert unit \"" + rawConvert + "\"");
			System.exit(1);
		}

		BigDecimal factor = conversionFactors.get(origin).divide(conversionFactors.get(convert), context);
		BigDecimal result = count.multiply(factor);
		return result;
	}
}
