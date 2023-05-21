package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica;

import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.PptrDto;

public interface PptrService extends SchedaTecnicaChild<PptrDto>
{

	Integer deleteNonAmmessi(UUID idPratica, int tipoProcedimento) throws Exception;

}
