package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoContenutoDTO;

/**
 * Row Mapper for table presentazione_istanza.tipo_contenuto
 */
public class TipoContenutoRowMapper implements RowMapper<TipoContenutoDTO>{

    public TipoContenutoDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final TipoContenutoDTO result = new TipoContenutoDTO();
        result.setId(rs.getInt("id"));
        result.setDescrizione(rs.getString("descrizione"));
        result.setDescrizioneEstesa(rs.getString("descrizione_estesa"));
        result.setMultiple(rs.getBoolean("multiple"));
        if (rs.getObject("sezione") != null)
            result.setSezione(rs.getString("sezione"));
        if (rs.getObject("check_pagamento") != null)
            result.setCheckPagamento(rs.getString("check_pagamento"));
        return result;
    }
}
