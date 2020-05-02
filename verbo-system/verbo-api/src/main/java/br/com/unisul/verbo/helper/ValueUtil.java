package br.com.unisul.verbo.helper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Collection;

public class ValueUtil {

	public static final String FL_SIM = "S";
	public static final String FL_ATIVO = "A";
	public static final String FL_TRUE = "true";
	public static final String FL_Y = "Y";

	public static final String FL_NAO = "N";
	public static final String FL_INATIVO = "I";
	public static final String FL_FALSE = "false";
	public static final String FL_N = "N";

	public static final Integer ZERO = Integer.valueOf(0);
	public static final Double ZEROD = Double.valueOf(0.0);
	public static final Double DELTA = Double.valueOf(0.00000001);
	public static final Integer NU_DECIMALS_DEFAULT = Integer.valueOf(2);

	private ValueUtil() {
		super();
	}

	/**
	 * @return Verdareiro se o objeto validado for NULL
	 */
	public static boolean isNull(Object value) {
		return value == null;
	}

	/**
	 * @return Verdareiro se o objeto validado não for NULL
	 */
	public static boolean isNotNull(Object value) {
		return !isNull(value);
	}

	/**
	 * @return Verdareiro se o objeto validado for vazio
	 */
	public static boolean isEmpty(final Object value) {
		if (value != null) {
			if (value instanceof String) {
				return isEmpty((String) value);
			} else if (value instanceof BigInteger) {
				return isEmpty((BigInteger) value);
			} else if (value instanceof Integer) {
				return isEmpty((Integer) value);
			} else if (value instanceof Long) {
				return isEmpty((Long) value);
			} else if (value instanceof BigDecimal) {
				return isEmpty((BigDecimal) value);
			} else if (value instanceof Double) {
				return isEmpty((Double) value);
			} else if (value instanceof Float) {
				return isEmpty((Float) value);
			} else if (value instanceof StringBuilder) {
				return isEmpty((StringBuilder) value);
			} else if (value instanceof Collection<?>) {
				return isEmpty((Collection<?>) value);
			}
			return false;
		}
		return true;
	}

	/**
	 * @return Verdareiro se o objeto validado for vazio
	 */
	public static boolean isEmpty(final String value) {
		return isNull(value) || value.trim().isEmpty();
	}

	/**
	 * @return Verdareiro se o objeto validado for vazio
	 */
	public static boolean isEmpty(final StringBuilder value) {
		return isNull(value) || isEmpty(value.toString());
	}

	/**
	 * @return Verdareiro se o objeto validado for vazio ou igual a zero
	 */
	public static boolean isEmpty(final BigInteger value) {
		return isNull(value) || value.equals(BigInteger.valueOf(ZERO));
	}

	/**
	 * @return Verdareiro se o objeto validado for vazio ou igual a zero
	 */
	public static boolean isEmpty(final Integer value) {
		return isNull(value) || value.equals(ZERO);
	}

	/**
	 * @return Verdareiro se o objeto validado for vazio ou igual a zero
	 */
	public static boolean isEmpty(final Long value) {
		return isNull(value) || value.equals(Long.valueOf(ZERO));
	}

	/**
	 * @return Verdareiro se o objeto validado for vazio ou igual a zero
	 */
	public static boolean isEmpty(final BigDecimal value) {
		return isNull(value) || value.equals(BigDecimal.valueOf(ZEROD)) || value.equals(BigDecimal.valueOf(ZERO));
	}

	/**
	 * @return Verdareiro se o objeto validado for vazio ou igual a zero
	 */
	public static boolean isEmpty(final Double value) {
		return isNull(value) || value.equals(Double.valueOf(ZERO));
	}

	/**
	 * @return Verdareiro se o objeto validado for vazio ou igual a zero
	 */
	public static boolean isEmpty(final Float value) {
		return isNull(value) || value.equals(Float.valueOf(ZERO));
	}

	/**
	 * @return Verdareiro se a lista validada for nula ou vazia
	 */
	public static boolean isEmpty(final Collection<?> list) {
		return isNull(list) || list.isEmpty();
	}

	/**
	 * @return Verdareiro se a lista validada for vazia
	 */
	public static boolean isEmpty(final Object[] list) {
		return isNull(list) || list.length == ZERO;
	}

	/**
	 * @return Verdareiro se o objeto validado não for vazio
	 */
	public static boolean isNotEmpty(final Object value) {
		return !isEmpty(value);
	}

	/**
	 * @return Verdareiro se o objeto validado não for vazio
	 */
	public static boolean isNotEmpty(final String value) {
		return !isEmpty(value);
	}

	/**
	 * @return Verdareiro se o objeto validado não for vazio
	 */
	public static boolean isNotEmpty(final StringBuilder value) {
		return !isEmpty(value);
	}

	/**
	 * @return Verdareiro se o objeto validado não for vazio ou maior que zero
	 */
	public static boolean isNotEmpty(final BigInteger value) {
		return !isEmpty(value);
	}

	/**
	 * @return Verdareiro se o objeto validado não for vazio ou maior que zero
	 */
	public static boolean isNotEmpty(final Integer value) {
		return !isEmpty(value);
	}

	/**
	 * @return Verdareiro se o objeto validado não for vazio ou maior que zero
	 */
	public static boolean isNotEmpty(final Long value) {
		return !isEmpty(value);
	}

	/**
	 * @return Verdareiro se o objeto validado não for vazio ou maior que zero
	 */
	public static boolean isNotEmpty(final Double value) {
		return !isEmpty(value);
	}

	/**
	 * @return Verdareiro se o objeto validado não for vazio ou maior que zero
	 */
	public static boolean isNotEmpty(final Float value) {
		return !isEmpty(value);
	}

	/**
	 * @return Verdareiro se a lista validada não for vazia
	 */
	public static boolean isNotEmpty(final Collection<?> list) {
		return !isEmpty(list);
	}

	/**
	 * @return Verdareiro se a lista validada não for vazia
	 */
	public static boolean isNotEmpty(final Object[] list) {
		return !isEmpty(list);
	}

	/**
	 * @return Verdareiro se a String validada for igual a "S"
	 */
	public static boolean isTrue(final String value) {
		return FL_SIM.equalsIgnoreCase(value);
	}

	/**
	 * @return Verdareiro se a String validada for igual a "N"
	 */
	public static boolean isFalse(final String value) {
		return FL_NAO.equalsIgnoreCase(value);
	}

	/**
	 * @return Verdareiro se a String validada for igual a "A"
	 */
	public static boolean isAtivo(final String value) {
		return FL_ATIVO.equalsIgnoreCase(value);
	}

	/**
	 * @return Verdareiro se a String validada for igual a "I"
	 */
	public static boolean isInativo(final String value) {
		return FL_INATIVO.equalsIgnoreCase(value);
	}

	public static String toString(Boolean value) {
		if (ValueUtil.isNotEmpty(value) && value) {
			return FL_SIM;
		} else {
			return FL_NAO;
		}
	}

	public static boolean notEquals(Double value1, Double value2) {
		return !equals(value1, value2);
	}

	public static boolean notEquals(Double value1, Double value2, Integer nuDecimals) {
		return !equals(value1, value2, nuDecimals);
	}

	public static boolean equals(Double value1, Double value2) {
		return equals(value1, value2, NU_DECIMALS_DEFAULT);
	}

	public static boolean equals(Double value1, Double value2, Integer nuDecimals) {
		return round(value1, nuDecimals).equals(round(value2, nuDecimals));
	}

	public static Double round(Double value) {
		return round(value, NU_DECIMALS_DEFAULT);
	}

	public static Double round(Double value, Integer nuDecimals) {
		BigDecimal bdValue = BigDecimal.valueOf(value);
		return bdValue.setScale(nuDecimals, RoundingMode.HALF_EVEN).doubleValue();
	}

	@SuppressWarnings("unchecked")
	public static <T> boolean valueBetween(T expected, T... values) {
		if (expected == null || values == null) {
			return false;
		}

		for (T value : values) {
			if (expected.equals(value)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static <T> boolean valueNotBetween(T expected, T... values) {
		return !valueBetween(expected, values);
	}

	public static boolean isFlagEnable(final String flag) {
		return (isNotEmpty(flag) && (FL_ATIVO.equalsIgnoreCase(flag) || FL_SIM.equalsIgnoreCase(flag)
				|| FL_TRUE.equalsIgnoreCase(flag) || FL_Y.equalsIgnoreCase(flag)));
	}

	public static boolean isFlagDisable(final String flag) {
		return !isFlagEnable(flag);
	}

	public static boolean isTrue(final Boolean value) {
		return Boolean.TRUE.equals(value);
	}

	public static boolean isFalse(final Boolean value) {
		return Boolean.FALSE.equals(value);
	}
}
