package it.eng.tz.puglia.autpae.service.interfacce;

import java.sql.SQLException;
import java.util.List;

import org.postgresql.util.PGobject;

import it.eng.tz.puglia.autpae.dto.InformazioniDTO;
import it.eng.tz.puglia.autpae.dto.LineaTemporaleDTO;
import it.eng.tz.puglia.autpae.dto.LocalizzazioneTabDTO;
import it.eng.tz.puglia.autpae.dto.TipologicaDestinatarioDTO;
import it.eng.tz.puglia.autpae.dto.ValidationBeanDto;

public interface AzioniService {

	public InformazioniDTO salva(InformazioniDTO body) throws Exception;
	public LocalizzazioneTabDTO salvaLocalizzazione(LocalizzazioneTabDTO body,Long idFascicolo) throws Exception;
	
	//public ValidationBeanDto valida(InformazioniDTO body) throws Exception;
	
//	public String valida(Long idFascicolo) throws Exception;
	
	public List<TipologicaDestinatarioDTO> getDestinatariTrasmissione(Long idFascicolo, List<TipologicaDestinatarioDTO> ulterioriDestinatari, boolean isBatchUser) throws Exception;

	public LineaTemporaleDTO lineaTemporale(long idFascicolo) throws Exception;

	public Boolean trasmetti(long idFascicolo, List<TipologicaDestinatarioDTO> ulterioriDestinatari) throws Exception;

	public void salvaInModifica(InformazioniDTO body) throws Exception;
	/**
	 * @autor Adriano Colaianni
	 * @date 29 apr 2021
	 * @param info
	 * @return
	 * @throws SQLException
	 */
	PGobject creaJsonInfo(InformazioniDTO info) throws SQLException;
	/**
	 * @autor Adriano Colaianni
	 * @date 10 mag 2021
	 * @param idFascicolo
	 * @param gruppoOwner codice del gruppo che ha creato la pratica (campo fascicolo.ufficio) 
	 * @return
	 * @throws Exception
	 */
	ValidationBeanDto valida(Long idFascicolo) throws Exception;
	
	/**
	 * restituisce l'id della corrispondenza relativa all'ultima trasmissione, eccezione se non la trova
	 * @autor Adriano Colaianni
	 * @date 31 ago 2021
	 * @param idFascicolo
	 * @return
	 * @throws Exception
	 */
	long getIdCorrispondenzaTrasmissione(Long idFascicolo) throws Exception;
	
}
