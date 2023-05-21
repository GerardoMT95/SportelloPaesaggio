package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.service;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DettaglioCorrispondenzaDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.RequestSaveComunicationDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.*;
import it.eng.tz.puglia.error.exception.CustomValidationException;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.repository.*;
import java.util.UUID;

/**
 * Service interface CRUD for table presentazione_istanza.relazione_ente
 */
public interface IRelazioneEnteService extends ICrudService<RelazioneEnteDTO, RelazioneEnteSearch, RelazioneEnteRepository>{

	public RelazioneEnteDTO findByIdPratica(UUID idPratica);

	void insertAllegato(UUID idAllegato, long idRelazione);

	public DettaglioCorrispondenzaDTO saveComunicazioneRelazione(RequestSaveComunicationDto reqComunication);
	
	public void validaEntity(final RelazioneEnteDTO entity) throws CustomValidationException;
}
