package dev.atne.convertit.core;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Math {
	public static final MathContext context = new MathContext(8, RoundingMode.HALF_UP);

	public static BigDecimal pow(BigDecimal a, BigDecimal b) {
		BigDecimal value = new BigDecimal(a.toString());
		return value;
	}
}
