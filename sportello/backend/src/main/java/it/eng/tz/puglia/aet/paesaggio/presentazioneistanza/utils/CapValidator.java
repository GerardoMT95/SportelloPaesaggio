package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils;

/**
 * Italian ZipCode (Codice di Avviamento Postale) CAP normalization and validation routines.
 * 5 numeric digits, from 00010 to 98168.
 * @author Ing. Fabio Lopez <fabio.lopez@eng.it>
 * @version 2020-03-26
 */
public class CapValidator {

	private static String regex = "^\\d{5}$";

	
	/**
	 * Verifies the basic syntax, length and control code of the given CAP.
	 * @param cap Raw CAP, possibly with spaces.
	 * @return Null if valid, or string describing why this CAP must be rejected.
	 */
	public static String validate(String cap)
	{
		cap = normalize(cap);
		if( cap.length() == 0 )
			return "Empty.";
		else if( cap.length() == 5 )
			return validate_temporary(cap);
		else
			return "Invalid length.";
	}

	
	/**
	 * Validates a CAP.
	 * @param cap Normalized, 5 digits CAP.
	 * @return Null if valid, or string describing why this CAP must be rejected.
	 */
	private static String validate_temporary(String cap)
	{
		if( ! cap.matches(regex) )
			return "Invalid characters.";
		
		int capValue = 0;
		try {
			capValue = Integer.parseInt(cap);
		} catch (NumberFormatException e) {
			return "Not a number.";
		}
		
		if( capValue == 0)
			return "Not a number.";
		
		if( capValue <10 )
			return "Min CAP is 00010.";
		
		if( capValue > 98168)
			return "Max CAP is 98168.";
		
		return null;
	}
	
	
	/**
	 * Normalizes a CAP by removing white spaces.
	 * Useful to clean-up user's input and to save the result in the DB.
	 * @param cap Raw CAP, possibly with spaces.
	 * @return Normalized CAP.
	 */
	public static String normalize(String cap)
	{
		if (cap==null)
			return null;
		
		cap = cap.replaceAll("[ \t\r\n]", "");
		cap = cap.trim();
		return cap;
	}
	
}
