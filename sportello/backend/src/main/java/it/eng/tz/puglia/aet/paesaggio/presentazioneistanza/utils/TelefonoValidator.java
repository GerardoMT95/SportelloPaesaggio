package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils;

/**
 * Italian and International Telphone Number normalization and validation routines.
 * @author Ing. Fabio Lopez <fabio.lopez@eng.it>
 * @version 2020-03-26
 */
public class TelefonoValidator {

	private static String regex = "^[+]{0,1}[0-9]{1,20}$";
	
	
	/**
	 * Verifies the basic syntax, length and control code of the given Telephone Number.
	 * @param tn Raw Telephone Number, possibly with spaces.
	 * @return Null if valid, or string describing why this Telephone Number must be rejected.
	 */
	public static String validate(String tn)
	{
		tn = normalize(tn);
		if( tn.length() == 0 )
			return "Empty.";
		else if( tn.length() > 20 )
			return "Too long.";
		else
			return validate_temporary(tn);
	}

	
	/**
	 * Validates a Telephone Number.
	 * @param tn Normalized.
	 * @return Null if valid, or string describing why this Telephone Number must be rejected.
	 */
	private static String validate_temporary(String tn)
	{
		if( ! tn.matches(regex) )
			return "Invalid characters.";
		
		return null;
	}
	
	
	/**
	 * Normalizes a Telephone Number by removing white spaces.
	 * Useful to clean-up user's input and to save the result in the DB.
	 * @param tn Raw Telephone Number, possibly with spaces.
	 * @return Normalized tn.
	 */
	public static String normalize(String tn)
	{
		if (tn==null)
			return null;
		
		tn = tn.replaceAll("[-_.)#(\t\r\n]", "");
		tn = tn.replaceAll(" ", "");
		tn = tn.trim();
		return tn;
	}
	
}
