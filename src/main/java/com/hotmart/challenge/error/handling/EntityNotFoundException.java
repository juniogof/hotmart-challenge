/**
 * 
 */
package com.hotmart.challenge.error.handling;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

/**
 * Throw this exception when a entity is not found
 * 
 * @author junio
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
@NoArgsConstructor
@SuppressWarnings("serial")
public class EntityNotFoundException extends RuntimeException {

	public EntityNotFoundException(Class<?> clazz, String... searchParamsMap) {
		super(EntityNotFoundException.generateMessage(clazz.getSimpleName(),
				toMap(String.class, String.class, searchParamsMap)));
	}

	private static String generateMessage(String entity, Map<String, String> searchParams) {
		return entity + " was not found for parameters " + searchParams;
	}

	private static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, String... entries) {
		if (entries.length % 2 == 1) {
			throw new IllegalArgumentException("Invalid entries");
		} else {
			return IntStream.range(0, entries.length / 2).map(i -> i * 2).collect(HashMap::new,
					(m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])), Map::putAll);
		}
	}

}
