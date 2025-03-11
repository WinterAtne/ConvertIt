package dev.atne.convertit.core;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class QuantityTest {

	Map<String, Unit> units = Map.ofEntries(
		Map.entry(
			"meter/second",
			new Unit(new BigDecimal[] {new BigDecimal("1"), new BigDecimal("1")},
						new BigDecimal[] {new BigDecimal("1"), new BigDecimal("1")})
		),

		Map.entry(
			"meter/second2",
			new Unit(new BigDecimal[] {new BigDecimal("1"), new BigDecimal("1")},
						new BigDecimal[] {new BigDecimal("-2"), new BigDecimal("1")})
		),

		Map.entry(
			"feet/minute2",
			new Unit(new BigDecimal[] {new BigDecimal("60"), new BigDecimal("0.3048")},
						new BigDecimal[] {new BigDecimal("-2"), new BigDecimal("1")})
		)
	);
	


	@Test
	void Conversion() {
		Quantity start = new Quantity(
				BigDecimal.ONE,
				units.get("meter/second2")
				);
		Quantity finalQuantity = new Quantity(
				new BigDecimal("11811.023"),
				units.get("feet/minute2")
				);
		
		Quantity converted = start.Convert(units.get("feet/minute2"));
		assertTrue(Quantity.IsEqual(converted, finalQuantity));
	}
}
