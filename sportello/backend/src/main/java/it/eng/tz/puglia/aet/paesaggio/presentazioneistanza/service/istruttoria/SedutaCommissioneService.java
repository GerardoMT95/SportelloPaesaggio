package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.istruttoria;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainNumberValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.AllegatiCommissioneLocaleDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.CLEntiCommissioniDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.CommissioneLocaleDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.SedutaCommissioneDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.SedutaCommissioneExtendedDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.SedutaCommissioneInputDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.SimpleAllegatiSedutaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.SedutaCommissioneSearch;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.PaesaggioOrganizzazioneDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.search.CommissioneLocaleSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface SedutaCommissioneService
{
	PaginatedList<SedutaCommissioneDto> search(SedutaCommissioneSearch search) throws Exception;
	SedutaCommissioneExtendedDTO find(Long idSeduta) throws Exception;
	SedutaCommissioneInputDTO insert(SedutaCommissioneInputDTO entity) throws Exception;
	SedutaCommissioneInputDTO update(SedutaCommissioneInputDTO entity) throws Exception;
	Integer delete(Long idSeduta) throws Exception;
	AllegatiCommissioneLocaleDTO upload(MultipartFile file, Integer tipoContenuto, List<UUID> pratiche, Long idSeduta) throws Exception;
	Integer remove(UUID idAllegato, Long idSeduta) throws Exception;
	void pubblicaSeduta(Long idSeduta) throws Exception;
	Boolean checkOrganizzazione(Long idSeduta) throws Exception;
	List<SimpleAllegatiSedutaDTO> searchAttachments(UUID idPratica) throws Exception;
	Boolean sedutaPresente(UUID idPratica) throws Exception;
	
	//creazione nuova commissione locale
	CommissioneLocaleDTO creaCommissione(CommissioneLocaleDTO entity) throws Exception;
	CommissioneLocaleDTO aggiornaCommissione(CommissioneLocaleDTO entity) throws Exception;
	CommissioneLocaleDTO findCommissioneLocale(Long idCommissione) throws Exception;
	CommissioneLocaleDTO findCommissioneByEnte(Long idEnte) throws Exception;
	PaginatedList<PaesaggioOrganizzazioneDTO> cercaCommissione(CommissioneLocaleSearch search) throws Exception;
	List<PlainNumberValueLabel> getEnti() throws Exception;
	List<PlainStringValueLabel> utentiAssociabili() throws Exception;
	List<PlainStringValueLabel> utentiCommissine(Long idCommissione) throws Exception;
	List<CLEntiCommissioniDTO> evaluateRangeDate(Long idCommissione, List<Long> enti, Date dataInizioVal, Date dataFineVal) throws Exception;
	List<CLEntiCommissioniDTO> evaluateRangeDate(List<Long> enti, Date dataInizioVal, Date dataFineVal) throws Exception;
	void aggiungiUtente(String username, Long idCommissione) throws Exception;
	void eliminaUtente(String username, Long idCommissione) throws Exception;
	
	void eliminaPraticaDaSeduteAttive(UUID idPratica) throws Exception;
}
