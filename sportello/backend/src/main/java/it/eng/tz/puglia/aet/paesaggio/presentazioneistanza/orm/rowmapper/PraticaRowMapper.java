package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.AttivitaDaEspletare;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoParere;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoRelazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoSeduta;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.PraticaDTO;

/**
 * Row Mapper for table presentazione_istanza.pratica
 */
public class PraticaRowMapper implements RowMapper<PraticaDTO>{

    public PraticaDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final PraticaDTO result = new PraticaDTO();
        result.setId( (java.util.UUID) rs.getObject("id"));
        result.setCodicePraticaAppptr(rs.getString("codice_pratica_appptr"));
        result.setEnteDelegato(rs.getString("ente_delegato"));
        if (rs.getObject("in_sanatoria") != null)
            result.setInSanatoria(rs.getBoolean("in_sanatoria"));
        result.setTipoProcedimento(rs.getInt("tipo_procedimento"));
        result.setStato(AttivitaDaEspletare.valueOf(rs.getString("stato")));
        result.setDataCreazione(rs.getDate("data_creazione"));
        result.setOggetto(rs.getString("oggetto"));
        result.setUserId(rs.getString("user_id"));
        result.setDataModifica(rs.getDate("data_modifica"));
        result.setPrivacyId(rs.getInt("privacy_id"));
        result.setValidazioneIstanza(rs.getBoolean("validazione_istanza"));
        result.setValidazioneSchedaTecnica(rs.getBoolean("validazione_scheda_tecnica"));
        result.setValidazioneAllegati(rs.getBoolean("validazione_allegati"));
        result.setValidazioneRichiedente(rs.getBoolean("validazione_richiedente"));
        result.setTipoSelezioneLocalizzazione(rs.getString("tipo_selezione_localizzazione"));
        result.setHasShape(rs.getBoolean("has_shape"));
        result.setNumeroProtocollo(rs.getString("numero_protocollo"));
        
        if(rs.getObject("ultimo_stato_valido") != null)
        	result.setUltimoStatoValido(AttivitaDaEspletare.valueOf(rs.getString("ultimo_stato_valido")));
        if(rs.getObject("stato_seduta_commissione") != null)
        	result.setStatoSedutaCommissione(StatoSeduta.valueOf(rs.getString("stato_seduta_commissione")));
        if(rs.getObject("stato_relazione_ente") != null)
        	result.setStatoRelazioneEnte(StatoRelazione.valueOf(rs.getString("stato_relazione_ente")));
        if(rs.getObject("stato_parere_soprintendenza") != null)
        	result.setStatoParereSoprintendenza(StatoParere.valueOf(rs.getString("stato_parere_soprintendenza")));
                
        result.setDataProtocollo(rs.getTimestamp("data_protocollo"));
        result.setDataPresentazione(rs.getTimestamp("data_presentazione"));
        result.setDataTrasmissioneProvvedimentoFinale(rs.getTimestamp("data_trasmissione_provvedimento_finale"));
        if(rs.getObject("data_inizio_lavorazione") != null)
        	result.setDataInizioLavorazione(rs.getTimestamp("data_inizio_lavorazione"));
        if(rs.getObject("data_trasmissione_verbale") != null)
        	result.setDataTrasmissioneVerbale(rs.getTimestamp("data_trasmissione_verbale"));
        if(rs.getObject("data_trasmissione_relazione") != null)
        	result.setDataTrasmissioneRelazione(rs.getTimestamp("data_trasmissione_relazione"));
        if(rs.getObject("data_trasmissione_parere") != null)
        	result.setDataTrasmissioneParere(rs.getTimestamp("data_trasmissione_parere"));
        if(rs.getObject("codice_trasmissione") != null)
        	result.setCodiceTrasmissione(rs.getString("codice_trasmissione"));
        if(rs.getObject("t_pratica_id") != null)
        	result.settPraticaId(rs.getLong("t_pratica_id"));
        if(rs.getObject("owner") != null)
        	result.setOwner(rs.getString("owner"));
        if(rs.getObject("ruolo_pratica") != null)
        	result.setRuoloPratica(rs.getString("ruolo_pratica"));
        if(rs.getObject("codice_segreto") != null)
        	result.setCodiceSegreto(rs.getString("codice_segreto"));
        if(rs.getObject("user_updated") != null)
        	result.setUserUpdated(rs.getString("user_updated"));
        if(rs.getObject("esonero_oneri") != null)
        	result.setEsoneroOneri(rs.getBoolean("esonero_oneri"));
        if(rs.getObject("esonero_bollo") != null)
        	result.setEsoneroBollo(rs.getBoolean("esonero_bollo"));

        return result;
    }
}
