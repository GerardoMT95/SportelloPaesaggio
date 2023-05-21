package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.istruttoria.IstrPraticaDTO;

public class IstrPraticaRowMapper implements RowMapper<IstrPraticaDTO>
{

	@Override
	public IstrPraticaDTO mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		IstrPraticaDTO dto = null;
		if(rs != null)
		{	
			PraticaRowMapper mapper = new PraticaRowMapper();
			dto = new IstrPraticaDTO(mapper.mapRow(rs, rowNum));
			dto.setRup(rs.getObject("rup") != null ? rs.getBoolean("rup") : null);
			dto.setUsernameFunzionario(rs.getObject("username_utente") != null ? rs.getString("username_utente") : null);
			dto.setDenominazioneFunzionario(rs.getObject("denominazione_utente") != null ? rs.getString("denominazione_utente") : null);
			dto.setUsernameRup(rs.getObject("username_rup") != null ? rs.getString("username_rup") : null);
			dto.setDenominazioneRup(rs.getObject("denominazione_rup") != null ? rs.getString("denominazione_rup") : null);
			dto.setUsernameAssegnatarioOrganizzazione(rs.getObject("username_assegnatario_organizzazione") != null ? rs.getString("username_assegnatario_organizzazione") : null);
			dto.setDenominazioneAssegnatarioOrganizzazione(rs.getObject("denominazione_assegnatario_organizzazione") != null ? rs.getString("denominazione_assegnatario_organizzazione") : null);
		}
		return dto;
	}
	
}
