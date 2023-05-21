package it.eng.tz.puglia.autpae.civilia.migration.rowmapper.putt;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.autpae.civilia.migration.dto.putt.InfoAutPaesAlfaBean;

public class InfoAutPaesAlfaRowMapper implements RowMapper<InfoAutPaesAlfaBean>
{

	@Override
	public InfoAutPaesAlfaBean mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		InfoAutPaesAlfaBean bean = null;
		if(rs != null)
		{
			bean = new InfoAutPaesAlfaBean();
			bean.setPraticaId(rs.getLong("t_pratica_id"));
			bean.setSuapDataAttivazione(rs.getString("dsuap_dataattivazionepratica"));
			bean.setCodicePratica(rs.getString("suap_codepratica"));
			if(rs.getNClob("suap_oggpratica") != null)
				bean.setSuapOggettoPratica(rs.getNClob("suap_oggpratica").getSubString(1, (int) rs.getNClob("suap_oggpratica").length()));
			if(rs.getNClob("Nnote") != null)
				bean.setnNote(rs.getNClob("Nnote").getSubString(1, (int) rs.getNClob("Nnote").length()));
			bean.setnCodiceinterno(rs.getString("Ncodiceinterno"));
			bean.setTipoIntervento(rs.getString("descrizioneintervento"));
			bean.setTipologia(rs.getString("tipologia_provvedimento"));
			bean.setResponsab(rs.getString("Nresponsabile_procedimento"));
			bean.setDataPro(rs.getString("ddata_provvedimento_finale"));
			bean.setNumeroProvvedimento(rs.getString("numero_provvedimento_finale"));
			bean.setEsitoProvvedimento(rs.getString("esito_richiesta"));
			bean.setRichiedente(rs.getString("Nrichiedente"));
			bean.setSanatoria(rs.getString("NSANATORIA"));
			bean.setIstatAmm(rs.getString("ISTAT_AMM"));
			//bean.setDataTrasmissione(rs.getTimestamp("DATA_TRASMISSIONE"));
		}
		return bean;
	}

}
