import dev.atne.convertit.*;

import org.junit.Test;

public class MainTest {
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
