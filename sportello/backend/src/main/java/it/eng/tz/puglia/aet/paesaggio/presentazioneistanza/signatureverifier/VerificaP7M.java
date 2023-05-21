package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.signatureverifier;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerificaP7M {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Metodo che riceve in input due file di cui il primo firmato digitalmente in formato p7m.
	 * Verifica la firma di quest'ultimo e successivamente verifica che il contenuto del file firmato sia uguale al contenuto 
	 * del file ricevuto come secondo parametro
	 * ritorna:
	 * -1 : verifica della firma digitale fallita
	 *  0 : tutto ok, verifica firma digitale e comparazione con file originale andata a buon fine
	 *  1 : verifica firma digitale ok ma contenuto del file firmato diverso da quello originale
	 * @author Giuseppe Canciello
	 * @date 11 giu 2019
	 * @param fileFirmato 
	 * @param fileOriginale
	 * @return
	 */
	public short verify(Path fileFirmato, Path fileOriginale) throws IOException {
		return this.verify(fileFirmato.toAbsolutePath().toString(), fileOriginale.toAbsolutePath().toString());
	}
	
	
	/**
	 * Metodo che riceve in input due array di byte relativi a due file di cui il primo firmato digitalmente in formato p7m.
	 * Verifica la firma di quest'ultimo e successivamente verifica che il contenuto del file firmato sia uguale al contenuto 
	 * del file ricevuto come secondo parametro
	 * ritorna:
	 * -1 : verifica della firma digitale fallita
	 *  0 : tutto ok, verifica firma digitale e comparazione con file originale andata a buon fine
	 *  1 : verifica firma digitale ok ma contenuto del file firmato diverso da quello originale
	 * @author Giuseppe Canciello
	 * @date 11 giu 2019
	 * @param fileFirmato 
	 * @param fileOriginale
	 * @return
	 */
	public short verify(String pathFileFirmato, String pathFileOriginale) {
		VerifySignature vs = null;
		try {
			DigitalSigner digitalSigner = DigitalSigner.getSingleton();
			digitalSigner.setDebugEnabled(true);
			
			//########################################################### 
			//		Verifica della firma digitale del fileFirmato		#   
			//###########################################################
			logger.info("VERIFICA FIRMA DIGITALE - STEP 1: Verifica della validità della firma digitale");
			try (InputStream is = new FileInputStream(pathFileFirmato)){
				vs = digitalSigner.verify(is);
				//Se la firma è valida vengono stampate a video le informazioni della firma
				if (vs.isVerified()) {
					logger.info("Firma valida...");
					ArrayList<ArrayList<String[]>> subjects = vs.getSubjects();
					for (ArrayList<String[]> subject : subjects) {
						for (int i = 0; i < subject.size(); i++) {
							String[] s = subject.get(i);
							logger.info(s[0] + " = " + s[1]);
						}
					}
				} else { //Altrimenti viene loggato il problema di firma non valida e viene ritornato -1 
					logger.info("Firma non valida!!");
					return -1;
				}
			}
			//#########################################################
			
			// ######################################################
			//						STEP 2 							#
			//			VERIFICA CHE IL CONTENUTO DEL				#
			//		FILE FIRMATO SIA UGUALE AL FILE ORIGINALE 		#
			//#######################################################
			logger.info("VERIFICA FIRMA DIGITALE - STEP 2: Verifica che il file sbustato sia uguale all'originale");
			// Creazione byte array del file sbustato
			try(InputStream isFirmato   = new FileInputStream(pathFileFirmato);
			    InputStream isOriginale = new FileInputStream(pathFileOriginale);
			){
				byte[] hashFileSbustato  = DigestUtils.sha1(digitalSigner.extractSignedContent(IOUtils.toByteArray(isFirmato)));
				byte[] hashFileOriginale = DigestUtils.sha1(isOriginale);
	
				if (Arrays.equals(hashFileSbustato, hashFileOriginale)) {
					logger.info("I file sono identici!!");
				}else {
					logger.info("I file non sono uguali!!");
					return 1;
				}
			}
			//########################################################
		} catch (Exception e) {
			logger.error("Error in verify", e);
			return -1;
		}
		return 0;
	}
}
