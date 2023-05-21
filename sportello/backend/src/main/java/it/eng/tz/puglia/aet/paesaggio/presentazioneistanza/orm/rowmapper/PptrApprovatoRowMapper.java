package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.pptr_approvato
 */
public class PptrApprovatoRowMapper implements RowMapper<PptrApprovatoDTO>{

    public PptrApprovatoDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final PptrApprovatoDTO result = new PptrApprovatoDTO();
        result.setPraticaId( (java.util.UUID) rs.getObject("pratica_id"));
        if (rs.getObject("ambito_paesaggistico") != null)
            result.setAmbitoPaesaggistico(rs.getString("ambito_paesaggistico"));
        if (rs.getObject("figure_ambito") != null)
            result.setFigureAmbito(rs.getString("figure_ambito"));
        if (rs.getObject("ricade_terr_art_1_03_co_5_6") != null)
            result.setRicadeTerrArt103Co56(rs.getBoolean("ricade_terr_art_1_03_co_5_6"));
        if (rs.getObject("ricade_terr_art_142_co_2") != null)
            result.setRicadeTerrArt142Co2(rs.getBoolean("ricade_terr_art_142_co_2"));
        return result;
    }
}
