package it.eng.tz.puglia.autpae.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.util.crypt.CryptUtil;
import it.eng.tz.puglia.autpae.dto.ConfigurazionePECBean;
import it.eng.tz.puglia.autpae.enumeratori.ProtocolloMailIn;
import it.eng.tz.puglia.autpae.enumeratori.ProtocolloMailOut;

public class ConfigurazionePECRowMapper implements RowMapper<ConfigurazionePECBean>
{
	@Override
	public ConfigurazionePECBean mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		ConfigurazionePECBean bean = null;
		if(rs != null)
		{
			bean = new ConfigurazionePECBean();
			bean.setId(rs.getInt("id"));
			bean.setPecIndirizzo(rs.getString("pec_indirizzo"));
			bean.setPecNome(rs.getString("pec_nome"));
			bean.setPecUsername(rs.getString("pec_username"));
			if(rs.getString("pec_password") != null)
				bean.setPecPassword(CryptUtil.decrypt(rs.getString("pec_password")));
			bean.setPecHostIn(rs.getString("pec_host_in"));
			bean.setPecHostOut(rs.getString("pec_host_out"));
			bean.setPecProtocolloIn(rs.getBoolean("pec_protocollo_in"));
			bean.setPecProtocolloOut(rs.getBoolean("pec_protocollo_out"));
			bean.setPecTipoProtocolloIn(ProtocolloMailIn.valueOf(rs.getString("pec_tipo_protocollo_in")));
			bean.setPecTipoProtocolloOut(ProtocolloMailOut.valueOf(rs.getString("pec_tipo_protocollo_out")));
			bean.setPecSslIn(rs.getBoolean("pec_ssl_in"));
			bean.setPecSslOut(rs.getBoolean("pec_ssl_out"));
			bean.setPecTlsIn(rs.getBoolean("pec_tls_in"));
			bean.setPecTlsOut(rs.getBoolean("pec_tls_out"));
			bean.setPecPortaSslIn(rs.getInt("pec_porta_ssl_in"));
			bean.setPecPortaSslOut(rs.getInt("pec_porta_ssl_out"));
			bean.setPecPortaTlsIn(rs.getInt("pec_porta_tls_in"));
			bean.setPecPortaTlsOut(rs.getInt("pec_porta_tls_out"));
			bean.setPecStartTlsIn(rs.getBoolean("pec_start_tls_in"));
			bean.setPecStartTlsOut(rs.getBoolean("pec_start_tls_out"));
			bean.setPecAutenticazioneIn(rs.getBoolean("pec_autenticazione_in"));
			bean.setPecAutenticazioneOut(rs.getBoolean("pec_autenticazione_out"));
		}
		return bean;
	}

}
