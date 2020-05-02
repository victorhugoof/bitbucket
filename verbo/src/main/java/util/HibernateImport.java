package util;

import java.io.IOError;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor;

public class HibernateImport extends MultipleLinesSqlCommandExtractor {
	private static final long serialVersionUID = 1L;
	private final String SOURCE_CHARSET = "UTF-8";

	@Override
	public String[] extractCommands(final Reader reader) {
		String[] lines = super.extractCommands(reader);

		Charset charset = Charset.defaultCharset();
		if (!charset.equals(Charset.forName(SOURCE_CHARSET))) {
			for (int i = 0; i < lines.length; i++) {
				try {
					lines[i] = new String(lines[i].getBytes(), SOURCE_CHARSET);
				} catch (UnsupportedEncodingException e) {
					throw new IOError(e);
				}
			}
		}

		return lines;
	}
}
