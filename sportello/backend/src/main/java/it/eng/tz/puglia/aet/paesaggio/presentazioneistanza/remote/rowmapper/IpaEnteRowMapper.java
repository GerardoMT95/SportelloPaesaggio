package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.remote.dto.IpaEnteDTO;

public class IpaEnteRowMapper implements RowMapper<IpaEnteDTO>
{

    @Override
    public IpaEnteDTO mapRow(ResultSet rs, int rowNum) throws SQLException
    {
	IpaEnteDTO bean = null;
	if(rs != null)
	{
	    bean = new IpaEnteDTO();
	    bean.setCodiceFiscale(rs.getString("codice_fiscale"));
	    bean.setCodiceIpa(rs.getString("codice_ipa"));
	    bean.setCodiceUo(rs.getString("codice_uo"));
	    bean.setNome(rs.getString("descrizione"));
	}
	return bean;
    }

}
