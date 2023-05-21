package it.eng.tz.puglia.geo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import com.esri.core.geometry.MapGeometry;
import com.esri.core.geometry.SpatialReference;

import it.eng.tz.puglia.autpae.config.ApplicationProperties;
import it.eng.tz.puglia.geo.util.GeometryUtils;
import it.eng.tz.puglia.geo.util.esri.EsriGeometryWithAttribute;

/**
 * oid,geom,id_fascicolo,vas_code,denominazione
 * @author acolaianni
 *
 */
public class EsriGeometryWithAttributeRowMapper implements RowMapper<EsriGeometryWithAttribute>{
	
	private EnumNomeVista vista;
	private static final Logger log = LoggerFactory.getLogger(EsriGeometryWithAttributeRowMapper.class);
	private ApplicationProperties props;
	
	public EsriGeometryWithAttributeRowMapper(EnumNomeVista vista,ApplicationProperties props) {
		super();
		this.vista = vista;
		this.props = props;
	}

	@Override
	public EsriGeometryWithAttribute mapRow(ResultSet rs, int rowNum) throws SQLException {
		EsriGeometryWithAttribute ret = new EsriGeometryWithAttribute();
		
		org.postgis.jts.JtsGeometry geom = (org.postgis.jts.JtsGeometry) rs.getObject("geometry");
		// converto in geometry esri
		// TODO make it faster with wkt directly
		MapGeometry esriMapGeom = null;
		try {
			esriMapGeom = GeometryUtils.esriJTSPostgisGeometryToEsriGeometry(geom);
			esriMapGeom.setSpatialReference(SpatialReference.create(32633));
		} catch (Exception e) {
			log.error("Errore in conversione JTStoEsriGeometry",e);
		}
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sft = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		ret.addAttribute("oid", rs.getLong("oid"));
		ret.addAttribute("id_fascicolo", rs.getLong("id_fascicolo"));
		ret.addAttribute("objectid", rs.getLong("oid"));
		ret.addAttribute("codice_pratica", rs.getString("codice_pratica"));
		ret.setGeometry(esriMapGeom);
		ret.addAttribute("comune", rs.getString("comuni"));
		ret.addAttribute("oggetto_intervento", rs.getString("oggetto_intervento"));
		ret.addAttribute("note", rs.getString("note"));
		ret.addAttribute("tipo_intervento", rs.getString("tipo_intervento"));
		ret.addAttribute("tipologia_autorizzazione", rs.getString("tipologia_autorizzazione"));
		ret.addAttribute("responsabile", rs.getString("responsabile"));
		ret.addAttribute("numero_procedimento", rs.getString("numero_procedimento"));
		ret.addAttribute("data_procedimento", rs.getDate("data_procedimento")==null?"":sf.format(rs.getDate("data_procedimento")));
		ret.addAttribute("esito_richiesta", rs.getString("esito_richiesta"));
		if(!this.props.isPutt()) {
		 ret.addAttribute("data_trasmissione", rs.getTimestamp("data_trasmissione")==null?"":sft.format(new java.sql.Date(rs.getTimestamp("data_trasmissione").getTime())));
		}
		ret.addAttribute("istat_amm_competente", rs.getString("istat_amm_competente"));
		if(this.props.isPutt()) {
			ret.addAttribute("sanatoria",rs.getBoolean("sanatoria")==true ?"vero":"");	
		}
		if(!vista.equals(EnumNomeVista.PUBLISHED)) {
			ret.addAttribute("user_id", rs.getString("user_id"));
		}
		ret.addAttribute("date_created", rs.getDate("date_created")==null?"":sf.format(rs.getDate("date_created")));
		if(vista.equals(EnumNomeVista.PUBLISHED)) {
			//url di accesso alla pratica
			StringBuilder sbUrl=new StringBuilder();
			sbUrl
			.append("<a ")
			.append(" href=\"")
			.append("/"+this.props.getCodiceApplicazione().toLowerCase()+"-fe")
			.append("/public/fascicolo?codiceFascicolo=")
			.append(rs.getString("codice_pratica"))
			.append("\" ")
			.append(" target=\"_blank\" ")
			.append(">")
			.append("Intercetta negli elenchi")
			.append("</a>")
			;
			ret.addAttribute("url_dettaglio", sbUrl.toString());
		}
		return ret;
	}

}
