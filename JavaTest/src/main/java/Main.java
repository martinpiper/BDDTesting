import com.sun.beans.finder.ClassFinder;
import wooity.api.java.OtherAnnotation;
import wooity.api.java.WooityFoo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 */
public class Main {

	public Main() {
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Hello world!");

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


		System.out.println("Goodbye world!");
	}
}
