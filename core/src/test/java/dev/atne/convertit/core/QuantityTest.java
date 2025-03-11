package dev.atne.convertit.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

class QuantityTest {
	
	@Test
	void Equality() {
		Quantity a = new Quantity(
				BigDecimal.ONE,
				new Unit(
					new BigDecimal[]{new BigDecimal("0.1"), new BigDecimal("0.2")},
					new BigDecimal[]{new BigDecimal("0.1"), new BigDecimal("0.2")}
					)
				);
		Quantity b = new Quantity(
				BigDecimal.ONE,
				new Unit(
					new BigDecimal[]{new BigDecimal("0.1"), new BigDecimal("0.2")},
					new BigDecimal[]{new BigDecimal("0.1"), new BigDecimal("0.2")}
					)
				);
		Quantity c = new Quantity(
				BigDecimal.ZERO,
				new Unit(
					new BigDecimal[]{new BigDecimal("0.1"), new BigDecimal("0.2")},
					new BigDecimal[]{new BigDecimal("0.1"), new BigDecimal("0.2")}
					)
				);
		Quantity d = new Quantity(
				BigDecimal.ONE,
				new Unit(
					new BigDecimal[]{new BigDecimal("100.0"), new BigDecimal("0.2")},
					new BigDecimal[]{new BigDecimal("0.1"), new BigDecimal("0.2")}
					)
				);

		assertTrue(Quantity.IsEqual(a, b));
		assertTrue(!Quantity.IsEqual(a, c));
		assertTrue(!Quantity.IsEqual(a, d));
	}

	@Test
	void Conversion() {
		Quantity start = new Quantity(
				BigDecimal.ONE,
				new Unit(
					new BigDecimal[]{new BigDecimal("1"), new BigDecimal("1")},
					new BigDecimal[]{new BigDecimal("-2"), new BigDecimal("1")}
					)
				);
		Unit convertTo = new Unit(
				new BigDecimal[]{new BigDecimal("60"), new BigDecimal("0.3048")},
				new BigDecimal[]{new BigDecimal("-2"), new BigDecimal("1")}
				);
		Quantity finalQuantity = new Quantity(
				new BigDecimal("11811.023"),
				convertTo
				);

		Quantity converted = start.Convert(convertTo);
		assertTrue(Quantity.IsEqual(converted, finalQuantity));
	}
}
