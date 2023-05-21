/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.dto;

import java.io.Serializable;

import org.hsqldb.lib.StringUtil;

/**
 * TNO_SOP_MATRICE_TERRITORIO destinatari pec per soprintendenza suddivisi per comune di competenza
 * @author Adriano Colaianni
 * @date 10 set 2021
 */
public class TnoSopMatriceTerritorio implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//ENTE_RIFERIMENTO,PEC_ENTE_RIFERIMENTO,PROV_RIFERIMENTO,MAIL_AGGIUNTIVA,COD_ISTAT,COMUNE
	private String enteRiferimento;
	private String pecEnteRiferimento;
	private String provRiferimento;
	private String mailAggiuntiva; //puo' essere a null
	private Long codIstat;
	private String comune;
	public String getEnteRiferimento() {
		return enteRiferimento;
	}
	public void setEnteRiferimento(String enteRiferimento) {
		this.enteRiferimento = enteRiferimento;
	}
	public String getPecEnteRiferimento(boolean isProduzione) {
		if(isProduzione || StringUtil.isEmpty(pecEnteRiferimento)) {
			return pecEnteRiferimento;
		}else {
			StringBuilder sb=new StringBuilder(pecEnteRiferimento);
			int idx = sb.lastIndexOf(".");
			int idxAt = sb.lastIndexOf("@");
			if(idx>0 && idxAt>0 && idx>idxAt) {
				sb.delete(idx, sb.length());
			}
			sb.append(".iot");
			return sb.toString();
		}
	}
	
	public void setPecEnteRiferimento(String pecEnteRiferimento) {
		this.pecEnteRiferimento = pecEnteRiferimento;
	}
	public String getProvRiferimento() {
		return provRiferimento;
	}
	public void setProvRiferimento(String provRiferimento) {
		this.provRiferimento = provRiferimento;
	}
	public String getMailAggiuntiva(boolean isProduzione) {
		if(isProduzione || StringUtil.isEmpty(mailAggiuntiva)) {
			return mailAggiuntiva;
		}else {
			StringBuilder sb=new StringBuilder(mailAggiuntiva);
			int idx = sb.lastIndexOf(".");
			int idxAt = sb.lastIndexOf("@");
			if(idx>0 && idxAt>0 && idx>idxAt) {
				sb.delete(idx, sb.length());
			}
			sb.append(".iot");
			return sb.toString();
		}
	}
	public void setMailAggiuntiva(String mailAggiuntiva) {
		this.mailAggiuntiva = mailAggiuntiva;
	}
	public Long getCodIstat() {
		return codIstat;
	}
	public void setCodIstat(Long codIstat) {
		this.codIstat = codIstat;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}
	

}
