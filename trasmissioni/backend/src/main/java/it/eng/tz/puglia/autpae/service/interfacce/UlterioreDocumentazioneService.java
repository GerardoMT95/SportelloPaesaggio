package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;
import it.eng.tz.puglia.autpae.dto.UlterioreDocumentazioneDTO;
import it.eng.tz.puglia.autpae.entity.AllegatoDTO;
import it.eng.tz.puglia.autpae.search.UlterioreDocumentazioneSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface UlterioreDocumentazioneService {
	
//	public ArrayList<UlterioreDocumentazioneDTO> datiDocumentazione(Long idFascicolo, Long idAllegato) throws Exception;

	public UlterioreDocumentazioneDTO inserisciDocumentazione(long idFascicolo, String titolo, String descrizione, List<TipologicaDestinatarioDTO> notificaA, List<String> visualizzabileDa, MultipartFile file) throws Exception;
	UlterioreDocumentazioneDTO inserisciDocumentazione(long idFascicolo, List<TipologicaDestinatarioDTO> notificaA, List<String> visualizzabileDa, List<AllegatoDTO> info, MultipartFile[] file) throws Exception;
	
	public PaginatedList<UlterioreDocumentazioneDTO> searchDocumentazione(UlterioreDocumentazioneSearch search) throws Exception;

	public List<TipologicaDestinatarioDTO> getDestinatariDefault() throws Exception;

	/**
	 * effettua la search senza impostazioni di visibilità, verranno fatte dal chiamante
	 * @autor Adriano Colaianni
	 * @date 19 lug 2021
	 * @param search
	 * @return
	 * @throws Exception
	 */
	PaginatedList<UlterioreDocumentazioneDTO> searchDocumentazioneWithoutCheckVisibilita(
			UlterioreDocumentazioneSearch search) throws Exception;
	
}