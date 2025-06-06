package dev.atne.convertit.app;

import java.math.BigDecimal;

import dev.atne.convertit.core.*;

public class Entry {

	public static void main(String[] Args) {
		if (Args.length < 3) { // Guard
			System.out.println("Not enought arguments");
			System.exit(1);
		}

		String rawCount = Args[0];
		String rawOrigin = Args[1];
		String rawConvert = Args[2];

		BigDecimal count = new BigDecimal(rawCount);
		Unit start = new Unit(rawOrigin);
		Quantity startQuantity = new Quantity(count, start);
		Unit convert = new Unit(rawConvert);

		Quantity convertedQuantity = startQuantity.Convert(convert);
		System.out.println(convertedQuantity.getValue());
	}
}
