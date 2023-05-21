package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils;

/**
 * Italian Codice Fiscale normalization and validation routines.
 * A <u>regular CF</u> is composed by 16 among letters and digits; the last
 * character is always a letter representing the control code.
 * A <u>temporary CF</u> could also be assigned; a temporary CF is composed of
 * 11 digits, the last digit being the control code.
 * Examples: MRORSS00A00A000U, 12345678903.
 * @author Umberto Salsi <salsi@icosaedro.it>
 * @version 2020-01-24
 */
public class CodiceFiscaleValidator {

	private static String regexNew = "^[A-Z]{6}[0-9LMNPQRSTUV]{2}[ABCDEHLMPRST]{1}[0-9LMNPQRSTUV]{2}[A-Z]{1}[0-9LMNPQRSTUV]{3}[A-Z]{1}$";
//	private static String regexOld = "^[0-9A-Z]{16}$";

	
	/**
	 * Normalizes a CF by removing white spaces and converting to upper-case.
	 * Useful to clean-up user's input and to save the result in the DB.
	 * @param cf Raw CF, possibly with spaces.
	 * @return Normalized CF.
	 */
	public static String normalize(String cf)
	{
		if (cf==null)
			return null;
		
		cf = cf.replaceAll("[ \t\r\n]", "");
		cf = cf.toUpperCase();
		return cf;
	}


	/**
	 * Verifies the basic syntax, length and control code of the given CF.
	 * @param cf Raw CF, possibly with spaces.
	 * @return Null if valid, or string describing why this CF must be rejected.
	 */
	public static String validate(String cf)
	{
		cf = normalize(cf);
		if( cf.length() == 0 )
			return "Empty.";
		else if( cf.length() == 16 )
			return validate_regular(cf);
		else if( cf.length() == 11 )
			return validate_temporary(cf);
		else
			return "Invalid length.";
	}


	/**
	 * Validates a regular CF.
	 * @param cf Normalized, 16 characters CF.
	 * @return Null if valid, or string describing why this CF must be rejected.
	 */
	private static String validate_regular(String cf)
	{
		if( ! cf.matches(regexNew) )
			return "Invalid characters.";
		int s = 0;
		String even_map = "BAFHJNPRTVCESULDGIMOQKWZYX";
		for(int i = 0; i < 15; i++){
			int c = cf.charAt(i);
			int n;
			if( '0' <= c && c <= '9' )
				n = c - '0';
			else
				n = c - 'A';
			if( (i & 1) == 0 )
				n = even_map.charAt(n) - 'A';
			s += n;
		}
		if( s%26 + 'A' != cf.charAt(15) )
			return "Invalid checksum.";
		return null;
	}


	/**
	 * Validates a temporary CF.
	 * @param cf Normalized, 11 characters CF.
	 * @return Null if valid, or string describing why this CF must be rejected.
	 */
	private static String validate_temporary(String cf)
	{
		if( ! cf.matches("^[0-9]{11}$") )
			return "Invalid characters.";
		int s = 0;
		for(int i = 0; i < 11; i++){
			int n = cf.charAt(i) - '0';
			if( (i & 1) == 1 ){
				n *= 2;
				if( n > 9 )
					n -= 9;
			}
			s += n;
		}
		if( s % 10 != 0 )
			return "Invalid checksum.";
		return null;
	}

}