/**
 */
public abstract class ConvertThis<T> {
	public abstract T transform(String string  , Class<?> targetType) throws NumberFormatException;
}
