package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;

/**
 * Row Mapper for table presentazione_istanza.allegati
 */
public class AllegatiRowMapper implements RowMapper<AllegatiDTO>{

    public AllegatiDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final AllegatiDTO result = new AllegatiDTO();
        result.setId( (java.util.UUID) rs.getObject("id"));
        result.setNomeFile(rs.getString("nome_file"));
        if (rs.getObject("formato_file") != null)
            result.setFormatoFile(rs.getString("formato_file"));
        if (rs.getObject("data_caricamento") != null)
            result.setDataCaricamento(rs.getTimestamp("data_caricamento"));
        if (rs.getObject("path_cms") != null)
            result.setPathCms(rs.getString("path_cms"));
        if (rs.getObject("id_cms") != null)
            result.setIdCms(rs.getString("id_cms"));
        result.setPraticaId( (java.util.UUID) rs.getObject("pratica_id"));
        if (rs.getObject("descrizione") != null)
            result.setDescrizione(rs.getString("descrizione"));
        if (rs.getObject("checksum") != null)
            result.setChecksum(rs.getString("checksum"));
        if (rs.getObject("size") != null)
            result.setSize(rs.getLong("size"));
        if (rs.getObject("deleted") != null)
            result.setDeleted(rs.getBoolean("deleted"));
        if (rs.getObject("size") != null)
            result.setIdIntegrazione(rs.getInt("integrazione_id"));
        if (rs.getObject("protocollo") != null)
            result.setChecksum(rs.getString("protocollo"));
        if (rs.getObject("titolo") != null)
            result.setTitolo(rs.getString("titolo"));
        if (rs.getObject("id_allegato_pre_protocollazione") != null)
            result.setIdAllegatoPreProtocollazione( (java.util.UUID) rs.getObject("id_allegato_pre_protocollazione"));
        if (rs.getObject("id_diogene_scheduler") != null)
            result.setIdDiogeneScheduler(rs.getString("id_diogene_scheduler"));
        	result.setSigned(rs.getBoolean("is_signed"));
        return result;
    }
}
