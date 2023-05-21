/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.service;

import java.io.File;
import java.io.IOException;

/**
 * @author Adriano Colaianni
 * @date 21 lug 2021
 */
public interface IProxyClientAlfresco {

	/**
	 * 
	 * @author acolaianni
	 *
	 * @param idCmis
	 * @param localPath se null restituisce un file locale temporaneo
	 * @return
	 * @throws IOException
	 */
	public File getDocumentIntoLocalFile(String idCmis,String localPath) throws IOException; 
}
