package it.eng.tz.puglia.autpae.generated.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.dto.BE_to_FE.organizzazioni.PaesaggioOrganizzazioneBean;

public class PaesaggioOrganizzazioneExtendedRowMapper implements RowMapper<PaesaggioOrganizzazioneBean>
{

	@Override
	public PaesaggioOrganizzazioneBean mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		PaesaggioOrganizzazioneBean bean = null;
		if(rs != null)
		{
			bean = new PaesaggioOrganizzazioneBean();
			bean.setId(rs.getInt("id"));
			if(rs.getObject("ente_id")!=null) {
				bean.setEnteId(rs.getInt("ente_id"));	
			}
	        bean.setTipoOrganizzazione(rs.getString("tipo_organizzazione"));
	        bean.setTipoOrganizzazioneSpecifica(rs.getString("tipo_organizzazione_specifica"));
	        bean.setCodiceCivilia(rs.getString("codice_civilia"));
	        bean.setRiferimentoOrganizzazione(rs.getInt("riferimento_organizzazione") != 0 ? rs.getInt("riferimento_organizzazione") : null);
	        bean.setDataCreazione(rs.getDate("data_creazione"));
	        bean.setDataTermine(rs.getDate("data_termine"));
 	        bean.setDenominazione(rs.getString("denominazione"));
 	        bean.setEnabled(rs.getBoolean("enabled"));
 	        bean.setDataScadenzaAbilitazione(rs.getDate("data_fine_abilitazione"));
		}
		return bean;
	}
	
}
