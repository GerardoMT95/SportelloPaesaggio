package it.eng.tz.puglia.autpae.generated.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.autpae.generated.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table procedimenti_ambientali.v_paesaggio_tipo_carica_documento
 */
public class VPaesaggioTipoCaricaDocumentoRowMapper implements RowMapper<VPaesaggioTipoCaricaDocumentoDTO>{

    public VPaesaggioTipoCaricaDocumentoDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final VPaesaggioTipoCaricaDocumentoDTO result = new VPaesaggioTipoCaricaDocumentoDTO();
        if (rs.getObject("id") != null)
            result.setId( (java.util.UUID) rs.getObject("id"));
        if (rs.getObject("nome") != null)
            result.setNome(rs.getString("nome"));
        if (rs.getObject("nome_famiglia") != null)
            result.setNomeFamiglia(rs.getString("nome_famiglia"));
        if (rs.getObject("nome_ente") != null)
            result.setNomeEnte(rs.getString("nome_ente"));
        if (rs.getObject("descrizione") != null)
            result.setDescrizione(rs.getString("descrizione"));
        if (rs.getObject("valore") != null)
            result.setValore(rs.getString("valore"));
        return result;
    }
}
