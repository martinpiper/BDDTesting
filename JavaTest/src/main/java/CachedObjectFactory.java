import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class CachedObjectFactory {
	private final Map<Class<?>, Object> instances = new HashMap<Class<?>, Object>();

	public void clear() {
		instances.clear();
	}

	public <T> T getInstance(Class<T> type) throws Exception {
		T instance = type.cast(instances.get(type));
		if (instance == null) {
			instance = cacheNewInstance(type);
		}
		return instance;
	}

	private <T> T cacheNewInstance(Class<T> type) throws Exception {
		try {
			Constructor<T> constructor = type.getConstructor();
			T instance = constructor.newInstance();
			instances.put(type, instance);
			return instance;
		} catch (NoSuchMethodException e) {
			throw new Exception(String.format("%s doesn't have an empty constructor.", type), e);
		} catch (Exception e) {
			throw new Exception(String.format("Failed to instantiate %s", type), e);
		}
	}
}
