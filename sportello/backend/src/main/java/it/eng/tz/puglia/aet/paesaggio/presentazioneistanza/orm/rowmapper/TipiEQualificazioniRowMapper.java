package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoQualificazione;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.enumeration.TipoStileQual;
import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipiEQualificazioniDTO;

/**
 * Row Mapper for table presentazione_istanza.tipi_e_qualificazioni
 */
public class TipiEQualificazioniRowMapper implements RowMapper<TipiEQualificazioniDTO>
{

    public TipiEQualificazioniDTO mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        TipiEQualificazioniDTO result = null;
        if(rs != null)
        {
        	result = new TipiEQualificazioniDTO();
            result.setCodice(rs.getString("codice"));
            result.setStile(TipoStileQual.valueOf(rs.getString("stile")));
            result.setRaggruppamento(TipoQualificazione.valueOf(rs.getString("raggruppamento")));
            result.setLabel(rs.getString("label"));
            result.setOrdine(rs.getInt("ordine"));
            result.setSezione(rs.getString("sezione"));
            if (rs.getObject("key") != null)
                result.setKey(rs.getInt("key"));
            if (rs.getObject("dependency") != null)
                result.setDependency(rs.getInt("dependency"));
            if (rs.getObject("hasText") != null)
                result.setHastext(rs.getBoolean("hasText"));
        }
        return result;
    }
}
