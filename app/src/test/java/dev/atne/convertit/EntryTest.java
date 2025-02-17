package dev.atne.convertit;

import org.junit.Test;

public class EntryTest {
	@Test
	public void testMain() {
		String[] sArg = new String[]{
			"5",
			"feet",
			"inches"
		};
		
		Entry.main(sArg);
	}
}
