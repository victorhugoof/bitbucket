package br.com.unisul.verbo.helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import br.com.unisul.verbo.exception.BusinessException;
import br.com.unisul.verbo.i18n.MensagensI18n.Mensagens;

public class Utils {

	@Autowired
	private static Logger log;

	private static final String S_DF = "###,###,###.00";
	private static final String S_DATA_F = "dd/MM/yyyy";
	private static final String S_DATA_HORA_F = "dd/MM/yyyy HH:mm:ss";
	private static final String ZONE = "America/Sao_Paulo";
	private static final String CONST_1_2 = "$1-$2";

	private static final DecimalFormat DF = new DecimalFormat(S_DF);
	private static final DateTimeFormatter DATA_F_UTC = DateTimeFormatter.ofPattern(S_DATA_F);
	private static final DateTimeFormatter DATA_HORA_F_UTC = DateTimeFormatter.ofPattern(S_DATA_HORA_F);

	private static final ZoneId ZONE_ID = ZoneId.of(ZONE);
	private static final DateTimeFormatter DATA_F_BRASIL = DateTimeFormatter.ofPattern(S_DATA_F).withZone(ZONE_ID);
	private static final DateTimeFormatter DATA_HORA_F_BRASIL = DateTimeFormatter.ofPattern(S_DATA_HORA_F)
			.withZone(ZONE_ID);

	private Utils() {
		super();
	}

	public static String l(String input, int width, char ch) {

		StringBuilder sb = new StringBuilder(input.trim());
		while (sb.length() < width) {
			sb.insert(0, ch);
		}

		if (sb.length() > width) {
			return sb.substring(0, width);
		}
		return sb.toString();
	}

	public static String r(String input, int width, char ch) {

		StringBuilder sb = new StringBuilder(input.trim());
		while (sb.length() < width) {
			sb.append(ch);
		}
		if (sb.length() > width) {
			return sb.substring(0, width);
		}
		return sb.toString();
	}

	public static String somenteNumeros(String valor) {
		if (ValueUtil.isNotEmpty(valor)) {
			return removeCharacterNotIn(valor, "0123456789");
		} else {
			return "";
		}
	}

	public static String removeCharacterNotIn(String s, String in) {
		StringBuilder r = new StringBuilder();
		if (s != null) {
			for (int i = 0; i < s.length(); i++) {
				for (int j = 0; j < in.length(); j++) {
					if (s.charAt(i) == in.charAt(j)) {
						r.append(s.charAt(i));
						break;
					}
				}
			}
		}
		return r.toString();
	}

	public static String formatNumFone(String ddi, String ddd, String numero) {
		return new StringBuilder().append(ddi).append(" ").append(formatNumFone(ddd, numero)).toString();
	}

	public static String formatNumFone(String ddd, String numero) {
		return new StringBuilder().append(ddd).append(" ").append(formatNumFone(numero)).toString();
	}

	public static String formatNumFone(String numero) {
		if (numero.length() >= 9) {
			return numero.replaceFirst("(\\d{5})(\\d+)", CONST_1_2);
		} else {
			return numero.replaceFirst("(\\d{4})(\\d+)", CONST_1_2);
		}
	}

	public static String formatNumCpfCnpj(String value) {

		if (ValueUtil.isNotEmpty(value)) {
			if (value.length() == 11) {
				return value.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d+)", "$1.$2.$3-$4");
			} else if (value.length() == 14) {
				return value.replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d+)", "$1.$2.$3/$4-$5");
			}
		}
		return value;
	}

	public static String formatNumCEP(String cep) {
		if (ValueUtil.isNotEmpty(cep) && cep.length() == 8) {
			return cep.replaceFirst("(\\d{5})(\\d+)", CONST_1_2);
		}
		return null;
	}

	public static String formatDouble(Double value) {
		if (ValueUtil.isNotEmpty(value)) {
			return DF.format(value);
		}
		return " - ";
	}

	public static String formatDataHoraUtc(ZonedDateTime value) {
		if (ValueUtil.isNotEmpty(value)) {
			return DATA_HORA_F_UTC.format(value);
		}
		return " - ";
	}

	public static String formatDataUtc(ZonedDateTime value) {
		if (ValueUtil.isNotEmpty(value)) {
			return DATA_F_UTC.format(value);
		}
		return " - ";
	}

	public static String formatDataUtc(LocalDate value) {
		if (ValueUtil.isNotEmpty(value)) {
			return DATA_F_UTC.format(value);
		}
		return " - ";
	}

	public static String formatDataHoraBrasil(ZonedDateTime value) {
		if (ValueUtil.isNotEmpty(value)) {
			return DATA_HORA_F_BRASIL.format(value);
		}
		return " - ";
	}

	public static String formatDataBrasil(ZonedDateTime value) {
		if (ValueUtil.isNotEmpty(value)) {
			return DATA_F_BRASIL.format(value);
		}
		return " - ";
	}

	public static String formatDataBrasil(LocalDate value) {
		if (ValueUtil.isNotEmpty(value)) {
			return DATA_F_BRASIL.format(value);
		}
		return " - ";
	}

	public static String formatFieldError(FieldError erro) {
		return StringUtils.capitalize(erro.getField() + " " + erro.getDefaultMessage());
	}

	public static String bytesToHex(byte[] bytes) {
		return Hex.encodeHexString(bytes).toUpperCase();
	}

	public static byte[] hexToBytes(String key) throws DecoderException {
		return Hex.decodeHex(key.toCharArray());
	}

	public static String getUrl(HttpServletRequest request) {

		String proto = request.getHeader("x-forwarded-proto");
		String host = request.getHeader("x-forwarded-host");
		return new StringBuilder().append(proto).append("://").append(host).toString();
	}

	public static String getLikeValue(final String texto) {
		return ValueUtil.isNotEmpty(texto) ? "%".concat(texto).concat("%") : "";
	}

	public static List<String> getLinhas(MultipartFile arquivo) {

		List<String> result = new ArrayList<>();
		try (InputStream is = arquivo.getInputStream()) {
			try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
				String line;
				while ((line = br.readLine()) != null) {
					result.add(line);
				}
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
		return result;
	}

	public static String getChave(int comprimento) {

		final String valores = "qwertyuiopasdfghjklzxcvbnm0123456789";
		StringBuilder chave = new StringBuilder();

		for (int i = 0; i < comprimento; i++) {

			int num = (new Random().nextInt() * valores.length());
			chave.append(valores.charAt(num));
		}
		return chave.toString().toUpperCase();
	}

	public static Date toDateString(String data) {
		try {
			DateFormat dateFormat = new SimpleDateFormat(S_DATA_F);
			return dateFormat.parse(data);
		} catch (ParseException e) {
			log.warn(e.getMessage(), e);
			return null;
		}
	}

	public static Date toDate(ZonedDateTime dateToConvert) {
		return java.sql.Timestamp.valueOf(dateToConvert.toLocalDateTime());
	}

	public static ZonedDateTime toZonedDateTime(String date) {
		try {
			return Objects.nonNull(date) ? toZonedDateTime(toDateString(date)) : null;
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	public static ZonedDateTime toZonedDateTime(Date dateToConvert) {
		return new java.sql.Timestamp(dateToConvert.getTime()).toLocalDateTime().atZone(ZONE_ID);
	}

	public static String format(BigDecimal value, int scale) {
		if (ValueUtil.isNotEmpty(value)) {
			return new DecimalFormat(r("#,##0.", scale + 6, '0')).format(value);
		}
		return " - ";
	}

	public static String getMensagemNaoEncontrado(Class<?> classe) {
		if (classe.getSimpleName().contains("Estado")) {
			return Mensagens.ESTADO_NAO_ENCONTRADO.getMensagem();
		}

		if (classe.getSimpleName().contains("Cidade")) {
			return Mensagens.CIDADE_NAO_ENCONTRADA.getMensagem();
		}

		if (classe.getSimpleName().contains("Produto")) {
			return Mensagens.PRODUTO_NAO_ENCONTRADO.getMensagem();
		}

		if (classe.getSimpleName().contains("Cliente")) {
			return Mensagens.CLIENTE_NAO_ENCONTRADO.getMensagem();
		}

		if (classe.getSimpleName().contains("Dependente")) {
			return Mensagens.DEPENDENTE_NAO_ENCONTRADO.getMensagem();
		}

		if (classe.getSimpleName().contains("CondicaoPagamento")) {
			return Mensagens.CONDICAO_PAGAMENTO_NAO_ENCONTRADA.getMensagem();
		}

		if (classe.getSimpleName().contains("FormaPagamento")) {
			return Mensagens.FORMA_PAGAMENTO_NAO_ENCONTRADA.getMensagem();
		}

		if (classe.getSimpleName().contains("Despesa")) {
			return Mensagens.DESPESA_NAO_ENCONTRADA.getMensagem();
		}

		if (classe.getSimpleName().contains("Entrada") && !classe.getSimpleName().contains("ItemEntrada")) {
			return Mensagens.ENTRADA_NAO_ENCONTRADA.getMensagem();
		}

		if (classe.getSimpleName().contains("GrupoAcesso")) {
			return Mensagens.GRUPO_ACESSO_NAO_ENCONTRADO.getMensagem();
		}

		if (classe.getSimpleName().contains("ItemEntrada") || classe.getSimpleName().contains("ItemVenda")) {
			return Mensagens.ITEM_NAO_ENCONTRADO.getMensagem();
		}

		if (classe.getSimpleName().contains("Venda") && !classe.getSimpleName().contains("ItemVenda")) {
			return Mensagens.VENDA_NAO_ENCONTRADA.getMensagem();
		}

		if (classe.getSimpleName().contains("Crediario") && !classe.getSimpleName().contains("ParcelaCrediario")) {
			return Mensagens.CREDIARIO_NAO_ENCONTRADO.getMensagem();
		}

		if (classe.getSimpleName().contains("ParcelaCrediario")) {
			return Mensagens.PARCELA_NAO_ENCONTRADA.getMensagem();
		}

		return Mensagens.NAO_ENCONTRADO.getMensagem();
	}

	public static Class<?> getClassType(Class<?> classe, int cont) {
		var thisType = classe.getGenericSuperclass();

		if (thisType instanceof ParameterizedType) {
			return (Class<?>) ((ParameterizedType) thisType).getActualTypeArguments()[cont];

		} else if (thisType instanceof Class) {
			return (Class<?>) ((ParameterizedType) ((Class<?>) thisType).getGenericSuperclass())
					.getActualTypeArguments()[cont];

		} else {
			throw new IllegalArgumentException("Problem handling type construction for " + classe);
		}
	}

	public static Object getObjetoFromClassType(Class<?> classeObj, int cont) {
		try {
			Class<?> classe = Utils.getClassType(classeObj, cont);
			Constructor<?> cons = classe.getConstructor();
			return cons.newInstance();
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

}
