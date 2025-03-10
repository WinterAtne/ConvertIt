package dev.atne.convertit.core;

import java.math.BigDecimal;

public class Quantity {
	private final Unit unit;
	private final BigDecimal value;

	public Quantity(BigDecimal value, Unit unit) {
		this.value = value;
		this.unit = unit;
	}

	public BigDecimal getValue() {
		return value;
	}

	public Unit getUnit() {
		return unit;
	}

	public Quantity Convert(Unit target) {
		return null;
	}
}
