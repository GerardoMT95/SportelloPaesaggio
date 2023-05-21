package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service;

import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.CdsDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PraticaCdsListSettingsDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.PlainStringValueLabel;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaCdsDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.PraticaCdsRepository;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.PraticaCdsSearch;
import it.eng.tz.puglia.error.exception.CustomValidationException;

public interface IPraticaCdsService extends ICrudService<PraticaCdsDTO, PraticaCdsSearch, PraticaCdsRepository> {

	PraticaCdsDTO getSettings(String idPratica, String id) throws Exception;

	PraticaCdsDTO getSettingsComitato(String idPratica) throws Exception;

	String newSettings(String idPratica) throws Exception;

	void deleteSettings(String idPratica, String id) throws Exception;

	List<PraticaCdsListSettingsDto> listSettings(String idPratica) throws Exception;

	boolean hasCds(String idPratica) throws Exception;

	void saveSettings(String idPratica, PraticaCdsDTO dto)throws Exception, CustomValidationException;

	void avvia(String idPratica, String id) throws Exception;

	List<PlainStringValueLabel> autocompleteProvince(String query);

	List<PlainStringValueLabel> autocompleteComuni(String siglaProvincia, String query);

	List<PlainStringValueLabel> listaTipo(String idPratica) throws Exception;

	List<PlainStringValueLabel> listaAttivita(String idPratica) throws Exception;

	List<PlainStringValueLabel> listaAzione(String attivita, String idPratica) throws Exception;

	List<PlainStringValueLabel> listaModalita();

	List<CdsDto> infoCds(String idPratica) throws Exception;

	CdsDto infoCds(String idPratica, String id) throws Exception;

	List<PlainStringValueLabel> listaComitato(String idPratica) throws Exception;

	List<PlainStringValueLabel> listaResponsabili(String idPratica) throws Exception;
	
	List<PlainStringValueLabel> listaFunzionari(String idPratica) throws Exception;
	
	Boolean canCreateConferenza(String idPratica) throws Exception;


}
