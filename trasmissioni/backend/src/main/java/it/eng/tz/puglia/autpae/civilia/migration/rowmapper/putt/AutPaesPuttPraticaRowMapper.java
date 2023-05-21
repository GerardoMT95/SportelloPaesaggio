package it.eng.tz.puglia.autpae.civilia.migration.rowmapper.putt;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.AutPaesPuttPraticaBean;

public class AutPaesPuttPraticaRowMapper implements RowMapper<AutPaesPuttPraticaBean>
{

	@Override
	public AutPaesPuttPraticaBean mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		AutPaesPuttPraticaBean bean = null;
		if(rs != null)
		{
			bean = new AutPaesPuttPraticaBean();
			bean.setId(rs.getLong("ID"));
			bean.setJbpId(rs.getLong("JBP_ID"));
			bean.setJbpUname(rs.getString("JBP_UNAME"));
			bean.setCodicePraticaApputp(rs.getString("CODICE_PRATICA_APPUTP"));
			bean.setCodicePraticaEntedelegato(rs.getString("CODICE_PRATICA_ENTEDELEGATO"));
			bean.settPraticaDescrizione(rs.getString("T_PRATICA_DESCRIZIONE"));
			bean.setEnteDelegato(rs.getString("ENTEDELEGATO"));
			bean.setUfficio(rs.getString("UFFICIO"));
			bean.setTnoPutpTipoprocedimentoId(rs.getLong("TNO_PUTP_TIPOPROCEDIMENTO_ID"));
			bean.setActive(rs.getShort("ACTIVE"));
			bean.setNote(rs.getString("NOTE"));
			bean.setProg(rs.getInt("PROG"));
			bean.setProvvedimentoSanatoria(rs.getString("PROVVEDIMENTO_SANATORIA"));
			bean.setSoloTrasmissione(rs.getShort("SOLOTRASMISSIONE"));
		}
		return bean;
	}

}
