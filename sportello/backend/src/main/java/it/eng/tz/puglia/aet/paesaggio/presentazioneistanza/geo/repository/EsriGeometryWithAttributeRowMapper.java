package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.geo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.esri.core.geometry.MapGeometry;
import com.esri.core.geometry.SpatialReference;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.controller.FascicoloController;
import it.eng.tz.puglia.geo.util.GeometryUtils;
import it.eng.tz.puglia.geo.util.esri.EsriGeometryWithAttribute;

/**
 * oid,geom,id_fascicolo,vas_code,denominazione
 * @author acolaianni
 *
 */
public class EsriGeometryWithAttributeRowMapper implements RowMapper<EsriGeometryWithAttribute>{
	
	private EnumNomeVista vista;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EsriGeometryWithAttributeRowMapper.class);
	
	public EsriGeometryWithAttributeRowMapper(EnumNomeVista vista) {
		super();
		this.vista = vista;
	}

	@Override
	public EsriGeometryWithAttribute mapRow(ResultSet rs, int rowNum) throws SQLException {
		EsriGeometryWithAttribute ret = new EsriGeometryWithAttribute();
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sft = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		ret.addAttribute("oid", rs.getInt("oid"));
		org.postgis.jts.JtsGeometry geom = (org.postgis.jts.JtsGeometry) rs.getObject("geom");
		// converto in geometry esri
		// TODO make it faster with wkt directly
		MapGeometry esriMapGeom = null;
		try {
			esriMapGeom = GeometryUtils.esriJTSPostgisGeometryToEsriGeometry(geom);
			esriMapGeom.setSpatialReference(SpatialReference.create(32633));
		} catch (Exception e) {
			LOGGER.error("Errore in conversione postgis geometry to esri geometry ",e);
		}
		ret.setGeometry(esriMapGeom);
		ret.addAttribute("id_fascicolo", (java.util.UUID)rs.getObject("id_fascicolo"));
		ret.addAttribute("oggetto_intervento", rs.getString("oggetto_intervento"));
		if(vista.equals(EnumNomeVista.PUBLISHED)) {
			ret.addAttribute("user_id", rs.getString("user_id"));
			ret.addAttribute("codice_pratica", rs.getString("codice_pratica"));
			ret.addAttribute("comuni", rs.getString("comuni"));
			ret.addAttribute("note", rs.getString("note"));
			ret.addAttribute("tipo_intervento", rs.getString("tipo_intervento"));
			ret.addAttribute("tipologia_autorizzazione", rs.getString("tipologia_autorizzazione"));
			ret.addAttribute("responsabile", rs.getString("responsabile"));
			ret.addAttribute("numero_procedimento", rs.getString("numero_procedimento"));
			ret.addAttribute("data_procedimento", rs.getDate("data_procedimento")==null?"":sf.format(rs.getDate("data_procedimento")));
			ret.addAttribute("esito_richiesta", rs.getString("esito_richiesta"));
			ret.addAttribute("data_trasmissione", rs.getTimestamp("data_trasmissione")==null?"":sft.format(new java.sql.Date(rs.getTimestamp("data_trasmissione").getTime())));
			ret.addAttribute("istat_amm_competente", rs.getString("istat_amm_competente"));
			ret.addAttribute("sanatoria",rs.getBoolean("sanatoria")==true ?"vero":"");
			//url di accesso alla pratica
			StringBuilder sbUrl=new StringBuilder();
			sbUrl
			.append("<a ")
			.append(" href=\"")
			.append("/autpae-fe")
			.append("/public/fascicolo?codiceFascicolo=")
			.append(rs.getString("codice_pratica"))
			.append("\" ")
			.append(" target=\"_blank\" ")
			.append(">")
			.append("Intercetta negli elenchi")
			.append("</a>")
			;
			ret.addAttribute("url_dettaglio", sbUrl.toString());
		}else {
			ret.addAttribute("id_fascicolo", (java.util.UUID)rs.getObject("id_fascicolo"));
			ret.addAttribute("date_created", rs.getDate("date_created"));
			ret.addAttribute("codice", rs.getString("codice"));
		}
		return ret;
	}

}
