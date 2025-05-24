package dev.atne.convertit.core;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class QuantityTest {

	Map<String, Unit> units = Map.ofEntries(
		Map.entry(
			"meter2",
			new Unit(new BigDecimal[] {new BigDecimal("1"), new BigDecimal("1")},
						new BigDecimal[] {new BigDecimal("1"), new BigDecimal("2")})
		),

		Map.entry(
			"feet2",
			new Unit(new BigDecimal[] {new BigDecimal("1"), new BigDecimal("0.3048")},
						new BigDecimal[] {new BigDecimal("1"), new BigDecimal("2")})
		),

		Map.entry(
			"meter/second2",
			new Unit(new BigDecimal[] {new BigDecimal("1"), new BigDecimal("1")},
						new BigDecimal[] {new BigDecimal("-2"), new BigDecimal("1")})
		),

		Map.entry(
			"feet/second2",
			new Unit(new BigDecimal[] {new BigDecimal("1"), new BigDecimal("0.3048")},
						new BigDecimal[] {new BigDecimal("-2"), new BigDecimal("1")})
		),

		Map.entry(
			"kilometer/hour2",
			new Unit(new BigDecimal[] {new BigDecimal("3600"), new BigDecimal("1000")},
						new BigDecimal[] {new BigDecimal("-2"), new BigDecimal("1")})
		)
	);
	


	@Test
	void Conversion() {
		Quantity start;
		Quantity end;
		Quantity converted;
		HashMap<Quantity, Quantity> before_after;

		// TEST 1
		start = new Quantity(
				new BigDecimal("2.48"),
				units.get("meter2")
				);
		end =  new Quantity(
				new BigDecimal("26.694497"),
				units.get("feet2")
				);
		converted = start.Convert(units.get("feet2"));
		System.out.println(converted.getValue().toString());
		assertTrue(Quantity.IsEqual(converted, end));

		// TEST 2
		start = new Quantity(
				BigDecimal.ONE,
				units.get("meter/second2")
				);
		end =  new Quantity(
				new BigDecimal("12960"),
				units.get("kilometer/hour2")
				);
		converted = start.Convert(units.get("kilometer/hour2"));
		System.out.println(converted.getValue().toString());
		assertTrue(Quantity.IsEqual(converted, end));
		
		// TEST 3
		start = new Quantity(
				new BigDecimal("2"),
				units.get("feet/second2")
				);
		end =  new Quantity(
				new BigDecimal("7900.416"),
				units.get("kilometer/hour2")
				);
		converted = start.Convert(units.get("kilometer/hour2"));
		System.out.println(converted.getValue().toString());
		assertTrue(Quantity.IsEqual(converted, end));

		// TEST 4
		start = new Quantity(
				new BigDecimal("2"),
				units.get("feet/second2")
				);
		end =  new Quantity(
				new BigDecimal("7900.416"),
				units.get("kilometer/hour2")
				);
		converted = start.Convert(units.get("kilometer/hour2"));
		System.out.println(converted.getValue().toString());
		assertTrue(Quantity.IsEqual(converted, end));
	}
}
