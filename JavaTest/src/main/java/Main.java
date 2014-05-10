import com.sun.beans.finder.ClassFinder;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import wooity.api.java.OtherAnnotation;
import wooity.api.java.WooityFoo;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class Main {

	public Main() {
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Hello world!");

/*
		// Technology test: Scan for all loaded class.
		// This would scan for all loaded classes linked into this application
		Reflections reflections = new Reflections( ClasspathHelper.forClass(Object.class), new SubTypesScanner(false));
		Collection<String> directSubtypes = reflections.getStore().get(SubTypesScanner.class).get(Object.class.getName());
		Set<String> allClasses = reflections.getStore().getSubTypesOf(Object.class.getName());
 */

		// Technology test: Find a named class
		Class<?> theClass = ClassFinder.findClass("com.somethingtest.gluecode.TestAnnotate");

		// Technology test: Cached object factory
		CachedObjectFactory theFactory = new CachedObjectFactory();
		Object fooity = theFactory.getInstance(theClass);
		System.out.println(fooity.toString());

		// Technology test: Locate all (WooityFoo,OtherAnnotation) annotated methods within a particular class
		List<PatternMatcher> annotatedMethods = new ArrayList<PatternMatcher>();

		if (theClass != null) {
			System.out.println(theClass.toString());
			for (Method method : theClass.getMethods()) {
				System.out.println(method.toString());
				Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
				for (Annotation annotation : declaredAnnotations) {
					if (annotation != null) {
						System.out.println(annotation.toString());
						if ( annotation instanceof WooityFoo) {
							WooityFoo temp = (WooityFoo) annotation;
							String[] values = temp.value();
							System.out.println("The value = " + Arrays.toString(values));

							for (String aValue : values) {
								PatternMatcher aPattern = new PatternMatcher();
								aPattern.pattern = Pattern.compile(aValue);
								aPattern.method = method;
								annotatedMethods.add(aPattern);
							}
						} else if ( annotation instanceof OtherAnnotation) {
							OtherAnnotation temp = (OtherAnnotation) annotation;
						}
					}
				}

			}
		}


		// Technology test: Execute some test against previously found annotation patterns
		for (PatternMatcher aPattern : annotatedMethods)  {

			System.out.println("Trying aPattern=" + aPattern.pattern.toString() + " for " + aPattern.method.toString());

			// This input string is a test for something read from an input feature file.
			// MPi: TODO: Read a feature file and parse it properly. Make it composable.
			Matcher matcher = aPattern.pattern.matcher("I create 2 foo things");

			if (matcher.lookingAt()) {
				List<String> arguments = null;
				arguments = new ArrayList<String>(matcher.groupCount());
				for (int i = 1; i <= matcher.groupCount(); i++) {
					int startIndex = matcher.start(i);
					arguments.add(matcher.group(i));
				}
				System.out.println("arguments" + arguments.toString());

				try {
					Object newClass = theFactory.getInstance(aPattern.method.getDeclaringClass());

					System.out.println("About to invoke: [" + aPattern.method.toString() + "] in: " + newClass.toString());

					ArgumentList argsList = new ArgumentList();
					Object[] array = argsList.create(arguments , aPattern.method);

					Main.invoke(newClass, aPattern.method , 10000000, array);

					System.out.println("Invoked: " + aPattern.method.toString());
				} catch (Throwable throwable) {
					throwable.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				}

			} else {
			}

		}


		System.out.println("Goodbye world!");
	}

	public static Object invoke(final Object target, final Method method, long timeoutMillis, final Object... args) throws Throwable {
		return Timeout.timeout(new Timeout.Callback<Object>() {
			@Override
			public Object call() throws Throwable {
				try {
					return method.invoke(target, args);
				} catch (IllegalArgumentException e) {
					throw new Exception("Fooity Failed to invoke " + method.toString(), e);
				} catch (InvocationTargetException e) {
					throw e.getTargetException();
				} catch (IllegalAccessException e) {
					throw new Exception("Fooity Failed to invoke " + method.toString(), e);
				}
			}
		}, timeoutMillis);
	}
}
