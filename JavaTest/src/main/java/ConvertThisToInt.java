/**
 */
public class ConvertThisToInt extends ConvertThis<Integer> {
	@Override
	public Integer transform(String string , Class<?> targetType) throws NumberFormatException {
		if ( !(targetType.isAssignableFrom(Integer.class) || targetType == int.class)) {
			throw new NumberFormatException();
		}
		return Integer.parseInt(string);
	}
}
