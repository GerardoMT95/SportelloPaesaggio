package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.ComuniCompetenzaDTO;

/**
 * Row Mapper for table presentazione_istanza.comuni_competenza
 */
public class ComuniCompetenzaRowMapper implements RowMapper<ComuniCompetenzaDTO>{

    public ComuniCompetenzaDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final ComuniCompetenzaDTO result = new ComuniCompetenzaDTO();
        result.setPraticaId((java.util.UUID) rs.getObject("pratica_id"));
        result.setEnteId(rs.getInt("ente_id"));
        result.setDataInserimento(rs.getTimestamp("data_inserimento"));
        result.setCodCat(rs.getString("cod_cat"));
        result.setCodIstat(rs.getString("cod_istat"));
        result.setDescrizione(rs.getString("descrizione"));
        result.setVincoloArt38(rs.getString("vincolo_art_38"));
        result.setVincoloArt100(rs.getString("vincolo_art_100"));
        result.setNotifica(rs.getBoolean("notifica"));
        return result;
    }
}
