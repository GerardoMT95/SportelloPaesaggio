package it.eng.tz.puglia.autpae.generated.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.autpae.generated.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table common.utente_attribute
 */
public class UtenteAttributeRowMapper implements RowMapper<UtenteAttributeDTO>{

    public UtenteAttributeDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final UtenteAttributeDTO result = new UtenteAttributeDTO();
        result.setApplicazioneId(rs.getInt("applicazione_id"));
        result.setUtenteId(rs.getLong("utente_id"));
        if (rs.getObject("pec") != null)
            result.setPec(rs.getString("pec"));
        if (rs.getObject("mail") != null)
            result.setMail(rs.getString("mail"));
        if (rs.getObject("birth_date") != null)
            result.setBirthDate(rs.getDate("birth_date"));
        if (rs.getObject("birth_place") != null)
            result.setBirthPlace(rs.getString("birth_place"));
        if (rs.getObject("phone_number") != null)
            result.setPhoneNumber(rs.getString("phone_number"));
        if (rs.getObject("mobile_number") != null)
            result.setMobileNumber(rs.getString("mobile_number"));
        if (rs.getObject("last_richiesta_abilitazione") != null)
            result.setLastRichiestaAbilitazione(rs.getLong("last_richiesta_abilitazione"));
        if (rs.getObject("create_time") != null)
            result.setCreateTime(rs.getTimestamp("create_time"));
        return result;
    }
}
