package it.eng.tz.puglia.aet.test;

import java.io.File;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.text.CaseUtils;
import org.springframework.util.StringUtils;

/**
 * Class with utility method to manage String
 * @author Antonio La Gatta
 * @date 23 ott 2018 
 */
public abstract class StringUtil {
	
	public static final String CHARSET_STRING = "UTF-8"; 
	public static final String CHARSET_STRING_LOWER = CHARSET_STRING.toLowerCase(); 
	public static final Charset CHARSET = Charset.forName(CHARSET_STRING); 
	private static final String OS_SEPARTOR = String.valueOf(File.separatorChar);
	private static final String SEPARTOR = "/";

	/**
	 * @author Antonio La Gatta
	 * @date 07 nov 2018
	 * @param obj
	 * @return String conversion of obj. See {@link String#valueOf(Object)}
	 */
	public static String toString(Object obj) {
		return String.valueOf(obj);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 20 set 2018
	 * @param tokens
	 * @return String concatenation of all string
	 */
	public static String concateneString(Object... tokens) {
		if(tokens == null)
			return null;
		StringBuilder sb = new StringBuilder();
		for(Object token : tokens) {
			if(token instanceof char[])
				sb.append(new String((char[])token));
			else
				sb.append(token);
		}
		return sb.toString();
	
	}
	/**
	 * @author Antonio La Gatta
	 * @date 15 giu 2018
	 * @param strings
	 * @return concatene string path
	 */
	public static String concateneStringPath(String... strings) {
		return concateneStringPath((Object[])strings);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 15 giu 2018
	 * @param objects
	 * @return concatene string path
	 */
	public static String concateneStringPath(Object...objects) {
		return concateneStringPath(OS_SEPARTOR, objects);
	}
	
	public static String concateneStringPathUnix(String... strings) {
		return concateneStringPathUnix((Object[])strings);
	}
	
	public static String concateneStringPathUnix(Object...objects) {		
		return concateneStringPath(SEPARTOR, objects);
	}
	
	private static String concateneStringPath(String sep, Object...objects) {
		StringBuilder sb = new StringBuilder();
		if(objects != null) {
			for(Object object : objects) {
				if((object == null || object.toString().startsWith(sep) == false)
				 && sb.toString().endsWith(sep) == false
				 && (sep.equals(SEPARTOR) || sb.length() > 0) 
				)
					sb.append(sep);
				sb.append(object);
			}
		}
		return sb.toString();
	}
	/**
	 * @author Antonio La Gatta
	 * @date 20 set 2018
	 * @param string
	 * @return true if string is empty
	 */
	public static boolean isEmpty(String string) {
		return StringUtils.isEmpty(string);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 20 set 2018
	 * @param string
	 * @return true if string is not empty
	 */
	public static boolean isNotEmpty(String string) {
		return !isEmpty(string);
	}

	/**
	 * @author Antonio La Gatta
	 * @date 08 nov 2018
	 * @param s
	 * @return add like for query
	 */
	public static String convertLike(Object s) {
		return concateneString("%", s, "%");
	}
	/**
	 * @author Antonio La Gatta
	 * @date 08 nov 2018
	 * @param s
	 * @return add left like for query
	 */
	public static String convertLeftLike(Object s) {
		return concateneString("%", s);
	}
	/**
	 * @author Antonio La Gatta
	 * @date 08 nov 2018
	 * @param s
	 * @return add right like for query
	 */
	public static String convertRightLike(String s) {
		return concateneString(s, "%");
	}
	/**
	 * mask password
	 * @author Antonio La Gatta
	 * @date 30 nov 2018
	 * @param s
	 * @return password masked
	 */
	public static String maskPassword(String s) {
		return StringUtil.isEmpty(s) ? "" : s.replaceAll(".", "*");
	}
	/**
	 * @author Antonio La Gatta
	 * @date 30 nov 2018
	 * @param s
	 * @return "" if s is null otherwise s.
	 */
	public static String cleanString(String s) {
		return s == null ? "" : s;
	}
	
	
	/**
	 * Metodo di utility per convertire un double in stringa con formato 1.234.232,00
	 * @author Giuseppe Canciello
	 * @date 6 mag 2019
	 * @param d
	 * @return
	 */
	public static String formatDoubleToString(Double d) {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ITALIAN);
		DecimalFormat df = (DecimalFormat)nf;
		String pattern="###,##0.00";
		df.applyPattern(pattern);
		String output = df.format(d);
		return output;
	}
	
	/**
	 * @author Antonio La Gatta
	 * @date 25 ott 2018
	 * @param s
	 * @param firstLetter if true first char is to upper case
	 * @return Camel Case string
	 */
	public static String camelCase(String s, boolean firstLetter) {
		return camelCase(s, firstLetter, '_');
	}
	/**
	 * @author Antonio La Gatta
	 * @date 25 ott 2018
	 * @param s
	 * @param firstLetter if true first char is to upper case
	 * @param separator is char separatorseparator
	 * @return
	 */
	public static String camelCase(String s, boolean firstLetter, char separator) {
		return CaseUtils.toCamelCase(s, firstLetter, '_');
	}
}
