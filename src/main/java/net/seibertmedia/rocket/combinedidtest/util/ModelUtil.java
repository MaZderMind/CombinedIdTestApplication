package net.seibertmedia.rocket.combinedidtest.util;

import java.util.List;
import java.util.function.Function;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class ModelUtil {

	private static final String[] AUDIT_FIELDS = { "created", "createdBy", "modified", "modifiedBy" };
	private static final int MAX_SHORTENED_STRING_LENGTH = 80;

	private ModelUtil() {

	}

	public static int reflectionHashCode(Object object) {
		return HashCodeBuilder.reflectionHashCode(object);
	}

	public static int reflectionHashCode(Object object, String... excludeFields) {
		return HashCodeBuilder.reflectionHashCode(object, excludeFields);
	}

	public static boolean reflectionEquals(Object objectA, Object objectB) {
		return EqualsBuilder.reflectionEquals(objectA, objectB);
	}

	public static boolean reflectionEquals(Object objectA, Object objectB, String... excludeFields) {
		return EqualsBuilder.reflectionEquals(objectA, objectB, excludeFields);
	}

	public static int hashCodeWithoutAudit(Object object) {
		return HashCodeBuilder.reflectionHashCode(object, AUDIT_FIELDS);
	}

	public static int hashCodeWithoutAudit(Object object, String... excludeFields) {
		return HashCodeBuilder.reflectionHashCode(object, ArrayUtils.addAll(AUDIT_FIELDS, excludeFields));
	}

	public static boolean equalsWithoutAudit(Object objectA, Object objectB) {
		return EqualsBuilder.reflectionEquals(objectA, objectB, AUDIT_FIELDS);
	}

	public static boolean equalsWithoutAudit(Object objectA, Object objectB, String... excludeFields) {
		return EqualsBuilder.reflectionEquals(objectA, objectB, ArrayUtils.addAll(AUDIT_FIELDS, excludeFields));
	}

	public static <T, R> R getSafe(T object, Function<? super T, R> getter) {
		return object == null ? null : getter.apply(object);
	}

	public static String shorten(String input) {
		return input == null ? "input" : StringUtils.abbreviate(input, MAX_SHORTENED_STRING_LENGTH);
	}

	public static String joinAndShortenList(List<?> list) {
		if (list == null) {
			return "null";
		}

		StringBuilder builder = new StringBuilder();

		for (Object object : list) {
			String string = "\"" + object + "\"";
			if (builder.length() > 0) {
				string = ", " + string;
			}

			builder.append(string);

			if (builder.length() > MAX_SHORTENED_STRING_LENGTH) {
				break;
			}
		}
		return "[" + shorten(builder.toString()) + "]";
	}
}
