package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.tariffa
 */
public class TariffaRowMapper implements RowMapper<TariffaDTO>{

    public TariffaDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final TariffaDTO result = new TariffaDTO();
        result.setId(rs.getLong("id"));
        result.setIdOrganizzazione(rs.getInt("id_organizzazione"));
        result.setIdTipoProcedimento(rs.getInt("id_tipo_procedimento"));
        result.setSogliaMinima(rs.getDouble("soglia_minima"));
        if (rs.getObject("soglia_massima") != null)
            result.setSogliaMassima(rs.getDouble("soglia_massima"));
        result.setDeleteEccedente(rs.getBoolean("delete_eccedente"));
        result.setPercentuale(rs.getDouble("percentuale"));
        result.setCifraDaAggiungere(rs.getDouble("cifra_da_aggiungere"));
        result.setPercentualeFinale(rs.getDouble("percentuale_finale"));
        result.setStartDate(rs.getTimestamp("start_date"));
        if (rs.getObject("end_date") != null)
            result.setEndDate(rs.getTimestamp("end_date"));
        result.setCreateDate(rs.getTimestamp("create_date"));
        result.setCreateUser(rs.getString("create_user"));
        return result;
    }
}
