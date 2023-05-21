package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.StatoSedutaCommissione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.SedutaCommissioneDTO;

/**
 * Row Mapper for table presentazione_istanza.seduta_commissione
 */
public class SedutaCommissioneRowMapper implements RowMapper<SedutaCommissioneDTO>{

    public SedutaCommissioneDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final SedutaCommissioneDTO result = new SedutaCommissioneDTO();
        result.setId(rs.getLong("id"));
        result.setIdOrganizzazione(rs.getInt("id_organizzazione"));
        result.setIdEnteDelegato(rs.getInt("id_ente_delegato"));
        result.setNomeSeduta(rs.getString("nome_seduta"));
        result.setDataSeduta(rs.getTimestamp("data_seduta"));
        if (rs.getObject("username_utente_creazione") != null)
            result.setUsernameUtenteCreazione(rs.getString("username_utente_creazione"));
        if (rs.getObject("data_creazione") != null)
            result.setDataCreazione(rs.getTimestamp("data_creazione"));
        if (rs.getObject("pubblica") != null)
        	result.setPubblica(rs.getBoolean("pubblica"));
        result.setStato(StatoSedutaCommissione.valueOf(rs.getString("stato")));
        return result;
    }
}
