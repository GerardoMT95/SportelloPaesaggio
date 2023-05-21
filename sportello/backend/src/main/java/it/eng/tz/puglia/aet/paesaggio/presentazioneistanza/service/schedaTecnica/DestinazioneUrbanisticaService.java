package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.service.schedaTecnica;

import java.util.List;
import java.util.UUID;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.fascicolo.schedaTecnica.DestinazioneUrbanisticaDto;

public interface DestinazioneUrbanisticaService extends SchedaTecnicaChild<List<DestinazioneUrbanisticaDto>>
{
	public List<DestinazioneUrbanisticaDto> saveOrUpdate(List<DestinazioneUrbanisticaDto> destinazione, UUID idPratica) throws Exception;

	public void valida(List<DestinazioneUrbanisticaDto> destinazione, UUID idPratica) throws Exception;
	
	public Integer deleteForComune(UUID idPratica, long idComune) throws Exception;

}