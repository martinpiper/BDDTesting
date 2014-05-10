/**
 */
public class ConvertThisToString extends ConvertThis<String> {
	@Override
	public String transform(String string , Class<?> targetType) throws NumberFormatException {
		if ( !targetType.isAssignableFrom(String.class)) {
			throw new NumberFormatException();
		}
		return string;
	}
}
