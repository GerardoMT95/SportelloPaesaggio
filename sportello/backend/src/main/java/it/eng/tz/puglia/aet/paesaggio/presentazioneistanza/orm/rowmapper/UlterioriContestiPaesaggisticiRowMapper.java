package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.UlterioriContestiPaesaggisticiDTO;

/**
 * Row Mapper for table presentazione_istanza.ulteriori_contesti_paesaggistici
 */
public class UlterioriContestiPaesaggisticiRowMapper implements RowMapper<UlterioriContestiPaesaggisticiDTO>
{

	public UlterioriContestiPaesaggisticiDTO mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		final UlterioriContestiPaesaggisticiDTO result = new UlterioriContestiPaesaggisticiDTO();
		result.setCodice(rs.getString("codice"));
		result.setLabel(rs.getString("label"));
		if (rs.getObject("art1") != null)
			result.setArt1(rs.getString("art1"));
		if (rs.getObject("definizione") != null)
			result.setDefinizione(rs.getString("definizione"));
		if (rs.getObject("disposizioni") != null)
			result.setDisposizioni(rs.getString("disposizioni"));
		if (rs.getObject("art2") != null)
			result.setArt2(rs.getString("art2"));
		if (rs.getObject("type") != null)
			result.setType(rs.getString("type"));
		if (rs.getObject("hasText") != null)
			result.setHastext(rs.getBoolean("hasText"));
//		if (rs.getObject("data_inizio_val") != null)
//			result.setDataInizioVal(rs.getDate("data_inizio_val"));
//		if (rs.getObject("data_fine_val") != null)
//			result.setDataFineVal(rs.getDate("data_fine_val"));
		if (rs.getObject("gruppo") != null)
			result.setGruppo(rs.getString("gruppo"));
		if (rs.getObject("sezione") != null)
			result.setSezione(rs.getString("sezione"));
		if (rs.getObject("order") != null)
			result.setOrder(rs.getInt("order"));
		return result;
	}
}
