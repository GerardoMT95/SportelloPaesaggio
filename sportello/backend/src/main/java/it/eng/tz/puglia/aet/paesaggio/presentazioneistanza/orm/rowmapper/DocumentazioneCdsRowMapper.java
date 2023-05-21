package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.dto.DocumentazioneCdsDto;

public class DocumentazioneCdsRowMapper implements RowMapper<DocumentazioneCdsDto> {

	@Override
	public DocumentazioneCdsDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		final DocumentazioneCdsDto result = new DocumentazioneCdsDto();
        result.setId(rs.getLong("id"));
        result.setIdConferenza(rs.getLong("id_conferenza"));
        if (rs.getObject("riferimento_conferenza") != null)
            result.setRiferimentoConferenza(rs.getString("riferimento_conferenza"));
        if (rs.getObject("stato_conferenza") != null)
            result.setStatoConferenza(rs.getString("stato_conferenza"));
        if (rs.getObject("nome") != null)
            result.setNome(rs.getString("nome"));
        if (rs.getObject("categoria") != null)
            result.setCategoria(rs.getString("categoria"));
        if (rs.getObject("tipo") != null)
            result.setTipo(rs.getString("tipo"));
        if (rs.getObject("protocollo") != null)
            result.setProtocollo(rs.getString("protocollo"));
//        if (rs.getObject("data_protocollo") != null)
//            result.setDataProtocollo(rs.getString("data_protocollo"));
        if (rs.getObject("data_creazione") != null)
            result.setDataCreazione(rs.getDate("data_creazione"));
        if (rs.getObject("id_tipo_documento") != null)
            result.setIdTipoDocumento(rs.getString("id_tipo_documento"));
        if (rs.getObject("tipo_documento") != null)
            result.setTipoDocumento(rs.getString("tipo_documento"));
        if (rs.getObject("rif_esterno") != null)
            result.setCmisId(rs.getString("rif_esterno"));
    	return result;
	}

}
