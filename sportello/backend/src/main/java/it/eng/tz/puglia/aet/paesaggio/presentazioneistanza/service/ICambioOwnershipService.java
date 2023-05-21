package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.CambioOwnershipRequestBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.CambioOwnershipResponseBean;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.SubentroDto;

/**
 * Servizio per cambio ownership
 * @author Antonio La Gatta
 * @date 6 lug 2021
 */
public interface ICambioOwnershipService {

	final static String STATO_BOZZA="B";
	final static String STATO_CONCLUSO="C";
	
	/**
	 * @author Antonio La Gatta
	 * @date 6 lug 2021
	 * @param request
	 * @return risultato
	 * @throws Exception
	 */
	CambioOwnershipResponseBean cercaOwnership(CambioOwnershipRequestBean request) throws Exception;
	/**
	 * Salva cambio ownership
	 * @author Antonio La Gatta
	 * @date 6 ago 2021
	 * @param codicePratica
	 * @param subentro
	 */
	void saveCambioOwnership(String codicePratica, SubentroDto subentro) throws Exception;
	/**
	 * Aggiunge modulo
	 * @author Antonio La Gatta
	 * @date 6 ago 2021
	 * @param codicePratica
	 * @param delega
	 */
	String addModulo(String codicePratica, MultipartFile delega) throws Exception;
	/**
	 * Elimina modulo
	 * @author Antonio La Gatta
	 * @date 6 ago 2021
	 * @param codicePratica
	 */
	void deleteModulo(String codicePratica) throws Exception;
	/**
	 * Aggiunge sollevamento
	 * @author Antonio La Gatta
	 * @date 6 ago 2021
	 * @param codicePratica
	 * @param sollevamento
	 */
	String addSollevamento(String codicePratica, MultipartFile sollevamento) throws Exception;
	/**
	 * Elimina sollevamento
	 * @author Antonio La Gatta
	 * @date 6 ago 2021
	 * @param codicePratica
	 */
	void deleteSollevamento(String codicePratica) throws Exception;

	/**
	 * Cambio ownership dopo che proponente è stato validato
	 * @author Antonio La Gatta
	 * @date 6 lug 2021
	 * @param request
	 * @return id pratica
	 * @throws Exception
	 */
	String cambiaOwnership(CambioOwnershipRequestBean request) throws Exception;
	/**
	 * Download delega
	 * @author Antonio La Gatta
	 * @date 6 ago 2021
	 * @param codicePratica
	 * @param response
	 */
	void downloadDelega(String codicePratica, HttpServletResponse response)throws Exception;
	/**
	 * Download delega
	 * @author Antonio La Gatta
	 * @date 6 ago 2021
	 * @param codicePratica
	 * @param response
	 */
	void downloadSollevamento(String codicePratica, HttpServletResponse response)throws Exception;
	
	/**
	 * 
	 * @author Simone Verna
	 * @date 11 lug 2022
	 * @param idPratica
	 * @throws Exception
	 */
	void sollevaIncarico(String idPratica) throws Exception;

}
