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
}
