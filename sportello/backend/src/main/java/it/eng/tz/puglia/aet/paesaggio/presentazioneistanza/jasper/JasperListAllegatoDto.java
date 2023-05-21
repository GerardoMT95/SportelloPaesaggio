package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.io.Serializable;
import java.util.List;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.AllegatiDTO;
import it.eng.tz.puglia.util.string.StringUtil;

/**
 * wrapper per subreport di elenchi allegato
 * @author acolaianni
 *
 */
public class JasperListAllegatoDto implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<JasperAllegatoDto> elenco;
	/**
	 * @return the elenco
	 */
	public List<JasperAllegatoDto> getElenco() {
		return elenco;
	}
	/**
	 * @param elenco the elenco to set
	 */
	public void setElenco(List<JasperAllegatoDto> elenco) {
		this.elenco = elenco;
	}
	

}
