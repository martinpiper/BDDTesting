import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * A method argument list matcher.
 */
public class ArgumentList {
	public ArrayList<ConvertThis<?> > toTry = new ArrayList<ConvertThis<?> >();

	public ArgumentList() {
		// Setup the list of conversions to automatically attempt
		toTry.add( new ConvertThisToString() );
		toTry.add( new ConvertThisToInt() );
	}

	// Returns an argument list that tries to match the target Method parameter types
	public Object[] create(List<String> arguments , Method method) throws NumberFormatException {
		Object[] array = new Object[arguments.size()];
		Class<?>[] destinationTypes = method.getParameterTypes();

		for( int i = 0 ; i < arguments.size() ; i++ ) {

			// MPi: TODO: Think about this. Perhaps we don't really want to try a simple cast, relying on these hidden conversions might be bad.
			// However some scripting languages do have a lot of "hidden" conversions.

			// First try a simple cast
			try {
				array[i] = destinationTypes[i].cast(arguments.get(i));
				// If it succeeds then stop trying
				break;
			} catch (Exception e) {
				// If there is an exception then no worries, just try the registered automatic options
			}

			for ( ConvertThis<?> trying : toTry ) {
				try {
					array[i] = trying.transform(arguments.get(i) , destinationTypes[i]);
					// If it succeeds then stop trying converters
					break;
				} catch (Exception e) {
					// If there is an exception then no worries, just try the next one
				}
			}
			// If nothing did the conversion then we abort the argument list creation
			if ( array[i] == null ) {
				throw new NumberFormatException("For method '" + method.toString() +"' Could not transform parameter " + i + " '" + arguments.get(i) + "' to type " + destinationTypes[i]);
			}
		}

		return array;
	}
}
