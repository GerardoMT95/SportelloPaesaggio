package it.eng.tz.puglia.autpae.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.eng.tz.puglia.autpae.dbMapping.ProcedimentoQualificazioni;
import it.eng.tz.puglia.autpae.dbMapping.TipiEQualificazioni;
import it.eng.tz.puglia.autpae.entity.TipiEQualificazioniDTO;
import it.eng.tz.puglia.autpae.enumeratori.Gruppi;
import it.eng.tz.puglia.autpae.enumeratori.TipoProcedimento;
import it.eng.tz.puglia.autpae.repository.base.TipiEQualificazioniBaseRepository;
import it.eng.tz.puglia.autpae.rowmapper.TipiEQualificazioniRowMapper;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * Full Repository for table tipi_e_qualificazioni
 */
@Repository
public class TipiEQualificazioniFullRepository  extends TipiEQualificazioniBaseRepository
{
	private static final Logger log = LoggerFactory.getLogger(TipiEQualificazioniFullRepository.class);
    private final TipiEQualificazioniRowMapper rowMapper = new TipiEQualificazioniRowMapper();
    
	
/*  public List<TipiEQualificazioniDTO> listByTipo(String tipo) throws Exception {
//		  log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
//
//        String sql = StringUtil.concateneString(" select * "                                             
//                                               ," from ", TipiEQualificazioni.getTableName()
//                                               ," where ", TipiEQualificazioni.tipo.columnName(), " = ?"
//                                               );
//        List<Object> parameters = new ArrayList<Object>();
//        parameters.add(tipo);
//        log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
//        return super.queryForList(sql, this.rowMapper, parameters);       
//    }	  */
    
    public List<TipiEQualificazioniDTO> selectByCodiceTipoProcedimento(TipoProcedimento tipoProcedimento,Long idFascicolo,Date dataRiferimentoConfigurazione,String gruppoOwnerFascicolo) throws Exception {
    	log.trace("Entrato nel repository: '"+this.getClass().getSimpleName()+"'. Metodo: '"+ new Object(){}.getClass().getEnclosingMethod().getName() + "'");
    	
    	Gruppi gruppoOwner=Gruppi.fromCodiceGruppoPM(gruppoOwnerFascicolo);
    	boolean isEti=gruppoOwner!=null &&gruppoOwner.equals(Gruppi.ETI_); 
    	StringBuilder sql = new StringBuilder(StringUtil.concateneString(
    			" select * "                                             
    			, " from " , TipiEQualificazioni.getTableName()
    			, " where  ", TipiEQualificazioni.id.getCompleteName()
    			, " IN  "    , 
    			" (SELECT ", 
    			  ProcedimentoQualificazioni.id_tipi_qualificazioni.getCompleteName(),
    			  " FROM ", ProcedimentoQualificazioni.getTableName(),
    			  " WHERE "
    			, ProcedimentoQualificazioni.codice_tipo_procedimento.getCompleteName()
    			, " = ? AND "
    			, ProcedimentoQualificazioni.date_start.getCompleteName()
    			," <= ? AND "
    			,"  ( "
    			, ProcedimentoQualificazioni.date_end.getCompleteName(),
    			" >= ? OR  "
    			, ProcedimentoQualificazioni.date_end.getCompleteName(),
    			" is null  "
    			, " ) "
    			,isEti?	" AND " + ProcedimentoQualificazioni.escluso_eti.getCompleteName()+" != true ":""
    			,") "
    			
    			)
    			);
    	List<Object> parameters = new ArrayList<Object>();
    	parameters.add(tipoProcedimento.name());
    	parameters.add(dataRiferimentoConfigurazione);
    	parameters.add(dataRiferimentoConfigurazione);
    	//non dovrebbe servire piu' con  la configurazione storica
//    	if(idFascicolo!=null) {
//    		String sqlIdTipiGiaSelezionati=
//    				" OR  " +
//    				TipiEQualificazioni.id.getCompleteName() +
//    				" IN " +
//    				"(SELECT  " +FascicoloIntervento.id_tipi_qualificazioni.getCompleteName() +
//    				" FROM " + 
//    				FascicoloIntervento.getTableName() +
//    				" WHERE " +
//    				FascicoloIntervento.id_fascicolo.getCompleteName() +" = ? )"  ;
//    		//aggiungo anche le selezioni già effettuate sul fascicolo
//    		parameters.add(idFascicolo);
//    		sql.append(sqlIdTipiGiaSelezionati);
//    	}
    	sql.append(
    			StringUtil.concateneString(
    			" order by "
    			, TipiEQualificazioni.zona  .getCompleteName() //, " ASC"
    			, ", "
    			, TipiEQualificazioni.ordine.getCompleteName() //, " ASC"
    			));
    	
    	log.trace("Eseguo la query: {} con i seguenti parametri: {}", sql, parameters);
    	return super.queryForList(sql.toString(), this.rowMapper, parameters);       
    }

}