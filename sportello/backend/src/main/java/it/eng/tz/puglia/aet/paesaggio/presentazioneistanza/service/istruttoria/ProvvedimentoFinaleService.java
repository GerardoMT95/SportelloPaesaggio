package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.ProvvedimentoFinaleDetailsDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.DestinatarioDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ProvvedimentoFinaleDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.ProvvedimentoFinaleSearch;

public interface ProvvedimentoFinaleService
{
	
	static String KEY_URL_DOWNLOAD_PREFIX="URL_DOWNLOAD_PREFIX";
	static String BASE_PUBLIC_DOWNLOAD = "downloadDocumentoDaMail";
	
	Long count(ProvvedimentoFinaleSearch search) throws Exception;
	ProvvedimentoFinaleDetailsDTO find(UUID idPratica) throws Exception;
	ProvvedimentoFinaleDetailsDTO find(UUID idPratica, Integer idOrganizzazione) throws Exception;
	ProvvedimentoFinaleDTO saveProvvedimento(ProvvedimentoFinaleDTO entity) throws Exception;
	ProvvedimentoFinaleDTO updateProvvedimento(ProvvedimentoFinaleDTO entity) throws Exception;
	AllegatiDTO uploadAllegatoProvvedimento(MultipartFile file, UUID idPratica) throws Exception;
	AllegatiDTO uploadAllegatoProvvedimento(MultipartFile file, List<Integer> tipiContenuto, UUID idPratica) throws Exception;
	void deleteAllegatoProvvedimento(UUID idAllegato) throws Exception;
	List<DestinatarioDTO> findDestinatariFissi(UUID idPratica, Integer idOrganizzazione) throws Exception;
	List<DestinatarioDTO> insertUlterioriDestinatari(List<DestinatarioDTO> ulterioriDestinatari, UUID idPratica) throws Exception;
	List<DestinatarioDTO> findDestinatari(UUID idPratica) throws Exception;
	ProvvedimentoFinaleDetailsDTO trasmettiProvvedimento(List<DestinatarioDTO> destinatari, UUID idPratica, String baseUrl) throws Exception;
	/**
	 * utilizzato per il retrieve lato pubbico dei provvedimenti
	 * @author acolaianni
	 *
	 * @param idPratica
	 * @return
	 * @throws Exception
	 */
	List<AllegatiDTO> findAllegatiProvvedimenti(UUID idPratica) throws Exception;
	List<AllegatiDTO> findDocumentiTrasmissione(UUID idPratica) throws Exception;
	/**
	 * genera l'anteprima della ricevuta di trasmissione
	 * @author acolaianni
	 *
	 * @param idPratica
	 * @param destinatari
	 * @return
	 * @throws Exception
	 */
	File generaAnteprimaPDFRicevutaTrasmissione(UUID idPratica, List<DestinatarioDTO> destinatari) throws Exception;
}
