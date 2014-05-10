import junit.framework.TestCase;
import org.junit.Test;

/**
 */
public class CachedObjectFactoryTest extends TestCase {
	@Test
	public void testClear() throws Exception {
		CachedObjectFactory foo = new CachedObjectFactory();

		Timeout t1 = foo.getInstance(Timeout.class);
		foo.clear();
		Timeout t2 = foo.getInstance(Timeout.class);

		assertNotSame(t1,t2);
	}

	@Test
	public void testGetInstance() throws Exception {
		CachedObjectFactory foo = new CachedObjectFactory();

		Timeout t1 = foo.getInstance(Timeout.class);
		Timeout t2 = foo.getInstance(Timeout.class);

		assertEquals(t1,t2);
	}
}
