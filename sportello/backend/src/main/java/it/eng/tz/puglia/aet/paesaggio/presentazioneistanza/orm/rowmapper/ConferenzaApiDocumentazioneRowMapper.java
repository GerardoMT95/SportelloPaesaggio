package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.cds.bean.ConferenzaApiDocumentazioneDto;
import it.eng.tz.puglia.cds.bean.enums.ConferenzaCategoriaDocumentoEnum;
import it.eng.tz.puglia.cds.bean.enums.ConferenzaTipologiaDocumentazioneEnum;

public class ConferenzaApiDocumentazioneRowMapper implements RowMapper<ConferenzaApiDocumentazioneDto>{

	@Override
	public ConferenzaApiDocumentazioneDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		final ConferenzaApiDocumentazioneDto result = new ConferenzaApiDocumentazioneDto();
        result.setNomeFile(rs.getString("nome_file"));
        result.setCmisId(rs.getString("id_cms"));
//        result.setNumeroProtocollo(rs.getString("numero_protocollo"));
//        result.setDataProtocollo(rs.getDate("data_protocollo"));
//        for(ConferenzaTipologiaDocumentazioneEnum tipo : ConferenzaTipologiaDocumentazioneEnum.values()){
//        	if(rs.getString("tipo").equals(tipo.name())) {
//        		result.setTipo(tipo);
//        	}
//        }
//        result.setCategoria(ConferenzaCategoriaDocumentoEnum.fromCodice(rs.getString("categoria")));
        return result;
	}

}
