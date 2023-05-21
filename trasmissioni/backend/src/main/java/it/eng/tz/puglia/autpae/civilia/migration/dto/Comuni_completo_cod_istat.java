/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.dto;

import org.hsqldb.lib.StringUtil;

/**
 * CIVILIA_CS.Comuni_completo_cod_istat  punta con sinonimo a PSITDATA
 * @author Adriano Colaianni
 * @date 20 apr 2021
 */
public class Comuni_completo_cod_istat {

	
	//@Column(name="COMUNE")
	private String comune;
	
	//@Column(name="PROVINCIA")
	private String provincia;	            
	              
	//@Column(name="CODICE_CATASTALE")
	private String codiceCatastale;
	
	//@Column(name="ISTAT_6_PROV")
	private String istat6province;

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCodiceCatastale() {
		return codiceCatastale;
	}

	public void setCodiceCatastale(String codiceCatastale) {
		this.codiceCatastale = codiceCatastale;
	}

	public String getIstat6province() {
		return istat6province;
	}

	public void setIstat6province(String istat6province) {
		this.istat6province = istat6province;
	}

	@Override
	public String toString() {
		return "Comuni_completo_cod_istat [comune=" + comune + ", provincia=" + provincia + ", codiceCatastale="
				+ codiceCatastale + ", istat6province=" + istat6province + "]";
	}
	
	public int getNumericIstat6Prov() {
		int ret=0;
		if(StringUtil.isEmpty(istat6province))
			return 0;
		try {		ret=Integer.parseInt(this.istat6province);		}catch(Exception e) {}
		return ret;
	}
	
}
