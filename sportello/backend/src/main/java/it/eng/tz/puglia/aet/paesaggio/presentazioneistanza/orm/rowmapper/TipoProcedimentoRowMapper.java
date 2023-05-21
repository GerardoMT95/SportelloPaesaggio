package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.*;
import java.math.*;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.*;
import org.springframework.jdbc.core.RowMapper;

/**
 * Row Mapper for table presentazione_istanza.tipo_procedimento
 */
public class TipoProcedimentoRowMapper implements RowMapper<TipoProcedimentoDTO>{

    public TipoProcedimentoDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
        final TipoProcedimentoDTO result = new TipoProcedimentoDTO();
        result.setId(rs.getInt("id"));
        result.setCodice(rs.getString("codice"));
        if (rs.getObject("descrizione") != null)
            result.setDescrizione(rs.getString("descrizione"));
        result.setInviaEmail(rs.getBoolean("invia_email"));
        result.setSanatoria(rs.getBoolean("sanatoria"));
        if (rs.getObject("abilitato_presentazione") != null)
            result.setAbilitatoPresentazione(rs.getBoolean("abilitato_presentazione"));
        if (rs.getObject("accertamento") != null)
            result.setAccertamento(rs.getBoolean("accertamento"));
        if (rs.getObject("descr_stampa") != null)
            result.setDescrStampa(rs.getString("descr_stampa"));
        if (rs.getObject("descr_stampa_titolo") != null)
            result.setDescrStampaTitolo(rs.getString("descr_stampa_titolo"));
        if (rs.getObject("descr_stampa_sottotitolo") != null)
            result.setDescrStampaSottoTitolo(rs.getString("descr_stampa_sottotitolo"));
        return result;
    }
}
