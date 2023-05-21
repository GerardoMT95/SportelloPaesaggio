package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.relazione_ente
 */
public class RelazioneEnteRowMapper implements RowMapper<RelazioneEnteDTO>{

    public RelazioneEnteDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final RelazioneEnteDTO result = new RelazioneEnteDTO();
        result.setIdRelazioneEnte(rs.getLong("id_relazione_ente"));
        result.setIdPratica( (java.util.UUID) rs.getObject("id_pratica"));
        result.setNumeroProtocolloEnte(rs.getString("numero_protocollo_ente"));
        result.setNominativoIstruttore(rs.getString("nominativo_istruttore"));
        if (rs.getObject("dettaglio_relazione") != null)
            result.setDettaglioRelazione(rs.getString("dettaglio_relazione"));
        if (rs.getObject("nota_interna") != null)
            result.setNotaInterna(rs.getString("nota_interna"));
        if (rs.getObject("id_corrispondenza") != null)
            result.setIdCorrispondenza(rs.getLong("id_corrispondenza"));
        return result;
    }
}
