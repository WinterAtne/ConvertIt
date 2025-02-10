class Main {
	// Args 0 is the number of units
	// Args 1 is the unit
	// Args 3 is the unit to convert to
	public static void main(String[] Args) {
		if (Args.length < 3) { // Guard
			System.out.println("Not enought arguments");
			return;
		}

		String rawCount = Args[0];
		String rawUnit = Args[1];
		String rawConversion = Args[2];
		
		Double count = Double.parseDouble(rawCount);

		if ("f" == rawUnit) {
			System.out.println(count * 12);
		} else {
			System.out.println(count / 12);
		}
	}
}
