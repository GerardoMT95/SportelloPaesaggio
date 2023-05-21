package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.tno_pptr_strumenti_urbanistici
 */
public class TnoPptrStrumentiUrbanisticiRowMapper implements RowMapper<TnoPptrStrumentiUrbanisticiDTO>{

    public TnoPptrStrumentiUrbanisticiDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final TnoPptrStrumentiUrbanisticiDTO result = new TnoPptrStrumentiUrbanisticiDTO();
        result.setId(rs.getLong("id"));
        if (rs.getObject("istat_6_prov") != null)
            result.setIstat6Prov(rs.getString("istat_6_prov"));
        if (rs.getObject("tipo_strumento") != null)
            result.setTipoStrumento(rs.getInt("tipo_strumento"));
        if (rs.getObject("stato") != null)
            result.setStato(rs.getString("stato"));
        if (rs.getObject("atto") != null)
            result.setAtto(rs.getString("atto"));
        if (rs.getObject("data_atto") != null)
            result.setDataAtto(rs.getDate("data_atto"));
        if (rs.getObject("utente_modifica") != null)
            result.setUtenteModifica(rs.getString("utente_modifica"));
        if (rs.getObject("data_modifica") != null)
            result.setDataModifica(rs.getDate("data_modifica"));
        return result;
    }
}
