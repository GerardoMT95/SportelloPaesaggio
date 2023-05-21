package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.adapter;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.FascicoloDto;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper.JasperAbstractDomandaDto;

public interface IJasperDichiarazioneAdapter<T extends JasperAbstractDomandaDto>
{
	T adapt(FascicoloDto fascicolo);
}
