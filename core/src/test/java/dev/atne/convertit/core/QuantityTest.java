package dev.atne.convertit.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class QuantityTest {
	
	@Test
	void SimpleConversions() {
		Map<Quantity, Quantity> tests = Map.of(
				new Quantity("1 foot"),					new Quantity("0.3048 meters"),
				new Quantity("1 minute"),				new Quantity("60 seconds")
				);

		for (var entry : tests.entrySet()) {
			Quantity result = Quantity.Convert(entry.getKey(), entry.getValue());
			assertTrue(result.isEqual(entry.getValue()));
		}
	}
}
