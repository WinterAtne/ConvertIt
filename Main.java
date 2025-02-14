import java.util.Map;
import java.math.BigDecimal;
import java.math.RoundingMode;

class Main {
	enum Unit {
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
		Map.entry(Unit.MINUTE,		new BigDecimal("60.0")),
		Map.entry(Unit.SECOND,		new BigDecimal("3600.0"))
	);

	// private static final int MAX_PRECISION = 2;

	public static BigDecimal convert(Unit origin, Unit convert, BigDecimal count) {
		BigDecimal factor = conversionFactors.get(origin).divide(conversionFactors.get(convert));
		BigDecimal result = count.multiply(factor);
		return result;
	}

	public static void main(String[] Args) {
		if (Args.length < 3) { // Guard
			System.out.println("Not enought arguments");
			System.exit(1);
		}

		String rawCount = Args[0];
		String rawOrigin = Args[1];
		String rawConvert = Args[2];

		BigDecimal count = new BigDecimal(rawCount);

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

		
		BigDecimal converted = convert(origin, convert, count);

		System.out.println(converted);

	}
}
