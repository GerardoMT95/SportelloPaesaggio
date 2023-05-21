package it.eng.tz.puglia.autpae.civilia.migration.rowmapper.putt;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.LocalizzazionePuttBean;

public class LocalizzazionePuttRowMapper implements RowMapper<LocalizzazionePuttBean>
{

	@Override
	public LocalizzazionePuttBean mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		LocalizzazionePuttBean bean = null;
		if(rs != null)
		{
			bean = new LocalizzazionePuttBean();
			bean.setComune(rs.getString("NCOMUNE"));
			bean.setCodCat(rs.getString("NCOD_CAT"));
			bean.setLivello(rs.getString("NLIVELLO"));
			bean.setSezione(rs.getString("NSEZIONE"));
			bean.setFoglio(rs.getString("NFOGLIO"));
			bean.setParticella(rs.getString("NPARTICELLA"));
			bean.setSub(rs.getString("NSUB"));
			bean.setDataRiferimentoCatastale(rs.getString("NDATA_RIFERIMENTO_CATASTALE"));
			bean.setCodicePratica(rs.getString("CODICE_PRATICA"));
			bean.setCodIstat(rs.getInt("NCOD_ISTAT"));
			bean.setPraticaId(rs.getLong("T_PRATICA_ID"));
		}
		return bean;
	}

}
