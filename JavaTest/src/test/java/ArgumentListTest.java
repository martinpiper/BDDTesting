import java.util.ArrayList;

import junit.framework.TestCase;
import org.junit.Test;

/**
 */
public class ArgumentListTest extends TestCase {

	class FooClass {
		public void aMethod1(int foo, String bar) {}
	}

	@Test
	public void testCreate() throws Exception {

		ArgumentList argsList = new ArgumentList();
		ArrayList<String> someValues = new ArrayList<String>();
		someValues.add("4");
		someValues.add("wibble");

		Object[] array = argsList.create(someValues , FooClass.class.getMethod("FooClass.aMethod1"));

		assertEquals(array[0].getClass() , int.class);
	}
}
