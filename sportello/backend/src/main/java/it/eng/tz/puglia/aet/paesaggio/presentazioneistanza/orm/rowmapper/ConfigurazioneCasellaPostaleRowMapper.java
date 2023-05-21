package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.configurazione_casella_postale
 */
public class ConfigurazioneCasellaPostaleRowMapper implements RowMapper<ConfigurazioneCasellaPostaleDTO>{

    public ConfigurazioneCasellaPostaleDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final ConfigurazioneCasellaPostaleDTO result = new ConfigurazioneCasellaPostaleDTO();
        result.setEmail(rs.getString("email"));
        result.setConfigurazione(rs.getString("configurazione"));
        return result;
    }
}
