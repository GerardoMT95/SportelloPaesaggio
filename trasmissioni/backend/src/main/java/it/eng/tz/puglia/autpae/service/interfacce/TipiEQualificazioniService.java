package it.eng.tz.puglia.autpae.service.interfacce;

import java.util.Date;
import java.util.List;

import it.eng.tz.puglia.autpae.entity.TipiEQualificazioniDTO;
import it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento;
import it.eng.tz.puglia.autpae.search.TipiEQualificazioniSearch;
import it.eng.tz.puglia.bean.PaginatedList;

public interface TipiEQualificazioniService {
	
	// BaseRepository
	public long count(TipiEQualificazioniSearch filter) throws Exception;
	public TipiEQualificazioniDTO find(Long pk) throws Exception;
//	public List<TipiEQualificazioniDTO> select() throws Exception;
//	public Long insert(TipiEQualificazioniDTO entity) throws Exception;
//	public int update(TipiEQualificazioniDTO entity) throws Exception;
//	public int delete(TipiEQualificazioniSearch search) throws Exception;
	public PaginatedList<TipiEQualificazioniDTO> search(TipiEQualificazioniSearch bean) throws Exception;
	// FullRepository
//	public List<TipiEQualificazioniDTO> listByTipo(String tipo) throws Exception;
	/**
	 * 
	 * @autor Adriano Colaianni
	 * @date 26 apr 2021
	 * @param tipoProcedimento
	 * @return
	 * @throws Exception
	 */
	public List<TipiEQualificazioniDTO> selectByCodiceTipoProcedimento(TipoProcedimento tipoProcedimento,Date dataRiferimentoConfigurazione,String gruppoOwner) throws Exception;
	/**
	 * serve per le pratiche migrate che hanno la selezione permanente rimovibile nella zona 3
	 * @autor Adriano Colaianni
	 * @date 26 apr 2021
	 * @param tipoProcedimento
	 * @param idFascicolo 
	 * @return
	 * @throws Exception
	 */
	//public List<TipiEQualificazioniDTO> selectByCodiceTipoProcedimentoPerMigrate(TipoProcedimento tipoProcedimento,Long idFascicolo) throws Exception;
	
}