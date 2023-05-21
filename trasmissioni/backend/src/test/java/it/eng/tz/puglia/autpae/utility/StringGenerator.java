package it.eng.tz.puglia.autpae.utility;

import java.util.Random;

/**
 * Utility per la generazione di {@link String} pseudocasuali.
 * @author <a href="mailto:Luciano.Faretra@eng.it">Luciano Faretra</a>
 * @version 1.1
 * @since 2019-05-09
 */
public class StringGenerator {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * Genera una stringa alfanumerica di una lunghezza definita dal parametro count.
     * @param count numero di caratteri di cui sarà composta la stringa in output
     * @return {@link String} generata automaticamente
     */
    public static String randomAlphaNumeric(int count) {
        Random random = new Random((System.nanoTime()*229));
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
          //int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            int character = (random.nextInt(ALPHA_NUMERIC_STRING.length()));
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    /**
     * Genera una stringa alfanumerica di una lunghezza definita dal parametro count.
     * @param pattern pattern per la generazione della stringa, eg. pattern={ABC} genererà solo stringhe contenente i tre caratteri definite
     * @param count numero di caratteri di cui sarà composta la stringa in output
     * @return {@link String} generata automaticamente
     */
    public static String randomAlphaNumeric(int count, String pattern) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
          //int character = (int)(Math.random()*pattern.length());
            int character = random.nextInt(pattern.length());
            builder.append(pattern.charAt(character));
        }
        return builder.toString();
    }
}
