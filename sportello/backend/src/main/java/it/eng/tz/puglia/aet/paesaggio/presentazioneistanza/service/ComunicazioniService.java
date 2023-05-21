package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.TipologicaAllegatoDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.Gruppi;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.CorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.CorrispondenzaSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.template_email.dto.TemplateEmailDestinatariDto;
import it.eng.tz.puglia.bean.PaginatedList;

public interface ComunicazioniService
{
	
	static String KEY_URL_DOWNLOAD_PREFIX="URL_DOWNLOAD_PREFIX";
	/**
	 * Rotta gestita da shibbolet, redirecta sulla pagina di autenticazione
	 * a livello di portale senza yoken wdo2
	 */
	static String BASE_PUBLIC_DOWNLOAD = "downloadDocumentoDaMail";
	static String PUBLIC_DOWNLOAD_ULT_DOC = BASE_PUBLIC_DOWNLOAD+"/documentoUlterioreDoc";
	static String PUBLIC_DOWNLOAD_ALLEGATO_MAIL = BASE_PUBLIC_DOWNLOAD+"/allegatoComunicazione";
	static String PUBLIC_DOWNLOAD_DOC_TRASM = BASE_PUBLIC_DOWNLOAD+"/ricevutaTrasmissione";
	
	DettaglioCorrispondenzaDTO find(Long idCorrispondenza) throws Exception;
	/**
	 * crea un bean ed effettua una insert nel repo corrispondenza
	 * riempie alcuni campi a partire dallo user autenticato
	 * @author acolaianni
	 *
	 * @param idPratica
	 * @return
	 * @throws Exception
	 */
	//DettaglioCorrispondenzaDTO create(UUID idPratica,Integer idOrganizzazione,Gruppi gruppo) throws Exception;
	/**
	 * 
	 * crea un bean ed effettua una insert nel repo corrispondenza
	 * riempie alcuni campi a partire dallo user autenticato
	 * @author acolaianni
	 *
	 * @param idPratica
	 * @param idOrganizzazione
	 * @param gruppo
	 * @param template nullable...
	 * @param isComunicazione true se è creata dal pannello comunicazioni, false viceversa (automatiche)
	 * @return
	 * @throws Exception
	 */
	DettaglioCorrispondenzaDTO create(UUID idPratica, Integer idOrganizzazione, Gruppi gruppo,
			TemplateEmailDestinatariDto template, boolean isComunicazione) throws Exception;
	DettaglioCorrispondenzaDTO create(UUID idPratica, Integer idOrganizzazione, Gruppi gruppo,
			TemplateEmailDestinatariDto template, boolean isComunicazione, String denominazioneMittente, String ruoloMittente) throws Exception;
	DettaglioCorrispondenzaDTO saveComunication(DettaglioCorrispondenzaDTO corrispondenza, UUID idPratica) throws Exception;
	DettaglioCorrispondenzaDTO saveComunication(DettaglioCorrispondenzaDTO corrispondenza, Integer idOrganizzazione) throws Exception;
	DettaglioCorrispondenzaDTO saveComunication(DettaglioCorrispondenzaDTO corrispondenza, UUID idPratica, String username, String gruppo, String ruolo) throws Exception;

	
	PaginatedList<DettaglioCorrispondenzaDTO> search(CorrispondenzaSearch filters) throws Exception;
	Integer eraseComunication(Long idCorrispondenza) throws Exception;
	TipologicaAllegatoDTO addAttachment(UUID idPratica, Long idCorrispondenza, MultipartFile attachment) throws Exception;
	Integer removeAttachment(UUID idPratica, UUID idAllegato) throws Exception;
	void sendComunication(Long idCorrispondenza, UUID idPratica) throws Exception;
	/**
	 * 
	 * @author acolaianni
	 *
	 * @param idCorrispondenza
	 * @param withProto true:  viene generato pdf di rendering comunicazione, viene timbrato e viene "aggiunto" agli allegati...
	 * @throws Exception
	 */
	void sendComunication(Long idCorrispondenza, UUID idPratica, boolean withProto) throws Exception;
	void sendComunication(DettaglioCorrispondenzaDTO comunicazione, UUID idPratica) throws Exception;
	void sendComunication(DettaglioCorrispondenzaDTO comunicazione, Integer idOrganizzazione) throws Exception;
	void sendComunication(DettaglioCorrispondenzaDTO comunicazione, UUID idPratica, Integer idOrganizzazione, String tipoInvia, List<AllegatiDTO> listaAllegati)
			throws Exception;
	void sendComunicationRelazione(DettaglioCorrispondenzaDTO comunicazione, UUID idPratica, String tipoInvia, Long idRelazione);
	
	File generaPDFinformazioniEmail(DettaglioCorrispondenzaDTO corrispondenza, List<AllegatiDTO> listaAllegati, Date data, String protocollo,PraticaDTO praticaDTO) throws Exception;
	DettaglioCorrispondenzaDTO create(UUID idPratica) throws Exception;
	void inserisciAllegatiCorrispondenzaDaRelazione(DettaglioCorrispondenzaDTO comunicazione, String tipoInvia,
			Long idRelazione) throws Exception;
	CorrispondenzaDTO getCorrispondenzaFromAllegato(UUID idAllegato);
	DettaglioCorrispondenzaDTO createAndSendComunication(DettaglioCorrispondenzaDTO comunicazione, UUID idPratica, Long idOrganizzazione, boolean isComunicazione) throws Exception;
	AllegatiDTO addAttachmentAndGetDetail(UUID idPratica, Long idCorrispondenza, MultipartFile attachment)
			throws Exception;
	
	void sendComunicazioneRichiedente(UUID idPratica, String codiceSegreto) throws Exception;
	void sendNuovoCodiceSegreto(UUID idPratica, String codiceSegreto) throws Exception;

	
}
