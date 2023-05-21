package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;

public class JasperClausoleEsoneriDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<JasperCheckBox> clausole;
	public List<JasperCheckBox> getClausole() {
		return clausole;
	}
	public void setClausole(List<JasperCheckBox> clausole) {
		this.clausole = clausole;
	}

}
