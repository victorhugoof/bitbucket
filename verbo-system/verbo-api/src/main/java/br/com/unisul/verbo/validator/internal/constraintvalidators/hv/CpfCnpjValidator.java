package br.com.unisul.verbo.validator.internal.constraintvalidators.hv;

import java.util.Objects;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraints.Mod11Check;
import org.hibernate.validator.internal.constraintvalidators.hv.Mod11CheckValidator;

import br.com.unisul.verbo.helper.Utils;
import br.com.unisul.verbo.i18n.MensagensI18n.Mensagens;
import br.com.unisul.verbo.validator.constraints.CpfCnpj;

public class CpfCnpjValidator implements ConstraintValidator<CpfCnpj, CharSequence> {

	private static final Pattern DIGITS_ONLY = Pattern.compile("\\d+");
	private static final Pattern SINGLE_DASH_SEPARATOR = Pattern.compile("\\d+-\\d\\d");

	private final Mod11CheckValidator cpfWithSeparatorMod11Validator1 = new Mod11CheckValidator();
	private final Mod11CheckValidator cpfWithSeparatorMod11Validator2 = new Mod11CheckValidator();

	private final Mod11CheckValidator cpfWithDashOnlySeparatorMod11Validator1 = new Mod11CheckValidator();
	private final Mod11CheckValidator cpfWithDashOnlySeparatorMod11Validator2 = new Mod11CheckValidator();

	private final Mod11CheckValidator cpfWithoutSeparatorMod11Validator1 = new Mod11CheckValidator();
	private final Mod11CheckValidator cpfWithoutSeparatorMod11Validator2 = new Mod11CheckValidator();

	private final Mod11CheckValidator cnpjWithSeparatorMod11Validator1 = new Mod11CheckValidator();
	private final Mod11CheckValidator cnpjWithSeparatorMod11Validator2 = new Mod11CheckValidator();

	private final Mod11CheckValidator cnpjWithoutSeparatorMod11Validator1 = new Mod11CheckValidator();
	private final Mod11CheckValidator cnpjWithoutSeparatorMod11Validator2 = new Mod11CheckValidator();

	@Override
	public void initialize(CpfCnpj constraintAnnotation) {

		// validates CPF strings with separator, eg 134.241.313-00
		// there are two checksums generated. The first over the digits prior the hyphen
		// with the first
		// check digit being the digit directly after the hyphen. The second checksum is
		// over all digits
		// pre hyphen + first check digit. The check digit in this case is the second
		// digit after the hyphen
		cpfWithSeparatorMod11Validator1.initialize(0, 10, 12, true, Integer.MAX_VALUE, '0', '0',
				Mod11Check.ProcessingDirection.RIGHT_TO_LEFT);
		cpfWithSeparatorMod11Validator2.initialize(0, 12, 13, true, Integer.MAX_VALUE, '0', '0',
				Mod11Check.ProcessingDirection.RIGHT_TO_LEFT);

		// validates CPF strings with separator, eg 134241313-00
		cpfWithDashOnlySeparatorMod11Validator1.initialize(0, 8, 10, true, Integer.MAX_VALUE, '0', '0',
				Mod11Check.ProcessingDirection.RIGHT_TO_LEFT);
		cpfWithDashOnlySeparatorMod11Validator2.initialize(0, 10, 11, true, Integer.MAX_VALUE, '0', '0',
				Mod11Check.ProcessingDirection.RIGHT_TO_LEFT);

		// validates CPF strings without separator, eg 13424131300
		// checksums as described above, except there are no separator characters
		cpfWithoutSeparatorMod11Validator1.initialize(0, 8, 9, true, Integer.MAX_VALUE, '0', '0',
				Mod11Check.ProcessingDirection.RIGHT_TO_LEFT);
		cpfWithoutSeparatorMod11Validator2.initialize(0, 9, 10, true, Integer.MAX_VALUE, '0', '0',
				Mod11Check.ProcessingDirection.RIGHT_TO_LEFT);

		// validates CNPJ strings with separator, eg 91.509.901/0001-69
		// there are two checksums generated. The first over the digits prior the hyphen
		// with the first
		// check digit being the digit directly after the hyphen. The second checksum is
		// over all digits
		// pre hyphen + first check digit. The check digit in this case is the second
		// digit after the hyphen
		cnpjWithSeparatorMod11Validator1.initialize(0, 14, 16, true, 9, '0', '0',
				Mod11Check.ProcessingDirection.RIGHT_TO_LEFT);
		cnpjWithSeparatorMod11Validator2.initialize(0, 16, 17, true, 9, '0', '0',
				Mod11Check.ProcessingDirection.RIGHT_TO_LEFT);

		// validates CNPJ strings without separator, eg 91509901000169
		// checksums as described above, except there are no separator characters
		cnpjWithoutSeparatorMod11Validator1.initialize(0, 11, 12, true, 9, '0', '0',
				Mod11Check.ProcessingDirection.RIGHT_TO_LEFT);
		cnpjWithoutSeparatorMod11Validator2.initialize(0, 12, 13, true, 9, '0', '0',
				Mod11Check.ProcessingDirection.RIGHT_TO_LEFT);

	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {

		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(Mensagens.CPF_CNPJ_INVALIDO.getMensagem())
				.addConstraintViolation();

		if (Objects.isNull(value)) {
			return true;
		}

		if (Utils.somenteNumeros(value.toString()).length() <= 11) {

			if (DIGITS_ONLY.matcher(value).matches()) {
				return cpfWithoutSeparatorMod11Validator1.isValid(value, context)
						&& cpfWithoutSeparatorMod11Validator2.isValid(value, context);
			} else if (SINGLE_DASH_SEPARATOR.matcher(value).matches()) {
				return cpfWithDashOnlySeparatorMod11Validator1.isValid(value, context)
						&& cpfWithDashOnlySeparatorMod11Validator2.isValid(value, context);
			} else {
				return cpfWithSeparatorMod11Validator1.isValid(value, context)
						&& cpfWithSeparatorMod11Validator2.isValid(value, context);
			}
		} else {

			if (DIGITS_ONLY.matcher(value).matches()) {
				return cnpjWithoutSeparatorMod11Validator1.isValid(value, context)
						&& cnpjWithoutSeparatorMod11Validator2.isValid(value, context);
			} else {
				return cnpjWithSeparatorMod11Validator1.isValid(value, context)
						&& cnpjWithSeparatorMod11Validator2.isValid(value, context);
			}
		}
	}

}
