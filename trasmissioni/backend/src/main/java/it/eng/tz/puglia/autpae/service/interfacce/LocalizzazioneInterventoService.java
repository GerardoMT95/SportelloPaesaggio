package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.List;

import it.eng.tz.puglia.autpae.entity.LocalizzazioneInterventoDTO;
import it.eng.tz.puglia.autpae.entity.composedKeys.LocalizzazioneInterventoPK;

public interface LocalizzazioneInterventoService {
	
	//BaseRepository
	public LocalizzazioneInterventoPK 		 insert (LocalizzazioneInterventoDTO entity) throws Exception;
	public int 						  		 update (LocalizzazioneInterventoDTO entity) throws Exception;
	
	//FullRepository
	public List<LocalizzazioneInterventoDTO> select (Long praticaId);
	public int 								 delete (LocalizzazioneInterventoDTO entity);
	public List<Long> 						 selectEffettivo(long idPratica); 
	
}