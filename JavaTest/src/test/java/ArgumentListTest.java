import junit.framework.TestCase;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;

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

		Class<?>[] params = new Class<?>[2];
		params[0] = int.class;
		params[1] = String.class;
		Method foo = FooClass.class.getMethod("aMethod1" , params);

		Object[] array = argsList.create(someValues , foo );

		// Integer.class because type argument (for abstract class ConvertThis<T>) cannot be a primitive (which int is) type
		assertEquals(array[0].getClass() , Integer.class);
		// The String
		assertEquals(array[1].getClass() , String.class);
	}
}
