package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service;

import java.util.List;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.SchedaTecnicaDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipiEQualificazioniDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.UlterioriContestiPaesaggisticiDTO;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search.UlterioriContestiPaesaggisticiSearch;

public interface SchedaTecnicaService
{
	List<TipiEQualificazioniDTO> findTipiEQualificazioni(Integer idTipoProcedimento) throws Exception;
	
	List<UlterioriContestiPaesaggisticiDTO> findUlterioreContestiPaesaggistici(UlterioriContestiPaesaggisticiSearch filters) throws Exception;
	
	SchedaTecnicaDto saveOrUpdate(SchedaTecnicaDto schedaTecnica) throws Exception;
	
	SchedaTecnicaDto find(UUID idPratica) throws Exception;

	boolean valida(UUID idPratica) throws Exception;
}
