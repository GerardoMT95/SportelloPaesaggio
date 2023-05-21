package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.utils;

/**
 * Italian Partita IVA normalization and validation routines.
 * A Partita IVA (PI) is composed of 11 digits; the last digit is the control
 * code. Example: 12345678903.
 * @author Umberto Salsi <salsi@icosaedro.it>
 * @version 2020-01-24
 */
public class PartitaIvaValidator {

	/**
	 * Normalizes a PI by removing white spaces.
	 * Useful to clean-up user's input and to save the result in the DB.
	 * @param pi Raw PI, possibly with spaces.
	 * @return Normalized PI.
	 */
	public static String normalize(String pi)
	{
		if (pi==null)
			return null;
		
		return pi.replaceAll("[ \t\r\n]", "");
	}


	/**
	 * Verifies the basic syntax, length and control code of the given PI.
	 * @param pi Raw PI, possibly with spaces.
	 * @return Null if valid, or string describing why this PI must be
	 * rejected.
	 */
	public static String validate(String pi)
	{
		pi = normalize(pi);
		if( pi.length() == 0 )
			return "Empty.";
		else if( pi.length() != 11 )
			return "Invalid length.";
		if( ! pi.matches("^[0-9]{11}$") )
			return "Invalid characters.";
		int s = 0;
		for(int i = 0; i < 11; i++){
			int n = pi.charAt(i) - '0';
			if( (i & 1) == 1 ){
				n *= 2;
				if( n  > 9 )
					n -= 9;
			}
			s += n;
		}
		if( s % 10 != 0 )
			return "Invalid checksum.";
		return null;
	}

}