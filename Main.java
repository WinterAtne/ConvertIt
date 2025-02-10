/*
 *
 * This program is almost always wrong
 *
*/
import java.util.Map;

class Main {
	enum Unit {
		NULL,
		// Imperial Length
		FOOT,
		INCH,
		// Metric Length
		METER,
	}

	private static final Map<String, Unit> unitNames = Map.ofEntries(
		Map.entry("feet", Unit.FOOT),
		Map.entry("inches", Unit.INCH),
		Map.entry("meters", Unit.METER)
	);

	// Map of all length conversion to meters
	private static final Map<Unit, Double> conversionFactors = Map.ofEntries(
		Map.entry(Unit.FOOT, 3.28084),
		Map.entry(Unit.INCH, 39.37),
		Map.entry(Unit.METER, 1.0)
	);

	public static Double convert(Unit origin, Unit convert, Double count) {
		return (count / conversionFactors.get(origin)) * conversionFactors.get(convert);
	}

	public static void main(String[] Args) {
		if (Args.length < 3) { // Guard
			System.out.println("Not enought arguments");
			System.exit(1);
		}

		String rawCount = Args[0];
		String rawOrigin = Args[1];
		String rawConvert = Args[2];

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

		Double count = Double.parseDouble(rawCount);
		
		Double converted = convert(origin, convert, count);

		System.out.println(converted);

	}
}
