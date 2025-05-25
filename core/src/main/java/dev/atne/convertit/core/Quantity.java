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
		assert(Unit.IsEqualDimensions(this.unit, target));

		BigDecimal newValue = this.value;

		for (int i = 0; i < Unit.DIMENSIONS; i++) {
			if (
				this.unit.getScalers()[i] == BigDecimal.ZERO ||
				this.unit.getVectors()[i] == BigDecimal.ZERO ||
				target.getScalers()[i] == BigDecimal.ZERO ||
				target.getVectors()[i] == BigDecimal.ZERO
				) { continue; }

			BigDecimal baseFactor = this.unit.getScalers()[i].divide(target.getScalers()[i], Math.context);
			BigDecimal factor = baseFactor.pow(target.getVectors()[i].intValue(), Math.context);
			newValue = newValue.multiply(factor, Math.context);
		}

		return new Quantity(newValue, target);
	}

	public static Boolean IsEqual(Quantity a, Quantity b) {
		if (!Unit.IsEqual(a.unit, b.unit)) {
			return false;
		}

		if (a.value.compareTo(b.value) != 0) {
			return false;
		}

		return true;
	}
}
