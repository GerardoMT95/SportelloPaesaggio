package it.eng.tz.puglia.autpae.civilia.migration.dto;

import java.io.Serializable;

import org.hsqldb.lib.StringUtil;

/**
 * /*
CREATE TABLE PSITPIANI.TNO_MAIL_ENTI
(
  TNO_MAIL_ENTI_ID      NUMBER,
  COD_ISTAT             NUMBER,
  ENTE_RIFERIMENTO      VARCHAR2(255 BYTE),
  PEC_ENTE_RIFERIMENTO  VARCHAR2(255 BYTE),
  TIPO_TERRITORIO       VARCHAR2(255 BYTE),
  OBJECTID              INTEGER                 NOT NULL,
  LOGO                  BLOB,
  INTESTAZIONE          VARCHAR2(400 BYTE),
  PIEPAGINA             VARCHAR2(4000 BYTE)
)
*/
/*
 * @author Adriano Colaianni
 * @date 10 set 2021
 */
public class TnoMailEnti implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	//@Column (name="TNO_MAIL_ENTI_ID")
	private long tnoMailEntiId;

	//@Column (name="COD_ISTAT")
	private long codIstat;

	//@Column (name="ENTE_RIFERIMENTO")
	private String enteRiferimento;
	               
	//@Column (name="PEC_ENTE_RIFERIMENTO")
	private String pecEnteRiferimento;
	        
	//@Column (name="TIPO_TERRITORIO") CO=comune PR=provincia RP=Regione Puglia
	private String tipoTerritorio;
	    
	//@Column (name="OBJECTID")
	private long objectid;
	
	//@Column (name="LOGO")
	private byte[] logo;
	         
	//@Column (name="INTESTAZIONE")
	private String intestazione;
	
	//@Column (name="PIEPAGINA")
	private String piePagina;

	public long getTnoMailEntiId() {
		return tnoMailEntiId;
	}

	public void setTnoMailEntiId(long tnoMailEntiId) {
		this.tnoMailEntiId = tnoMailEntiId;
	}

	public long getCodIstat() {
		return codIstat;
	}

	public void setCodIstat(long codIstat) {
		this.codIstat = codIstat;
	}

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

	public String getTipoTerritorio() {
		return tipoTerritorio;
	}

	public void setTipoTerritorio(String tipoTerritorio) {
		this.tipoTerritorio = tipoTerritorio;
	}

	public long getObjectid() {
		return objectid;
	}

	public void setObjectid(long objectid) {
		this.objectid = objectid;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public String getIntestazione() {
		return intestazione;
	}

	public void setIntestazione(String intestazione) {
		this.intestazione = intestazione;
	}

	public String getPiePagina() {
		return piePagina;
	}

	public void setPiePagina(String piePagina) {
		this.piePagina = piePagina;
	}
		
}
