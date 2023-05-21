package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica;

import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.SchedaTecnicaDescrizioneDto;

public interface DescrizioneService extends SchedaTecnicaChild<SchedaTecnicaDescrizioneDto>
{

	int deleteValueNonAmmessi(UUID praticaId, int tipoProcedimento);

}
