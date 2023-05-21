package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import java.sql.Date;
import java.util.UUID;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

public class UlterioreDocSearch extends BaseSearchRequestBean {

	private static final long serialVersionUID = -4487565073043952439L;
	
	private Date dataCondivisioneDa;
	private Date dataCondivisioneA;
	private String inseritoDa;
	private String visibileA;
	private String destinatarioNotifica;
	private String descrizioneContenuto;
	private String titoloDocumento;
	private UUID idFascicolo;
	
	
	public Date getDataCondivisioneDa() {
		return dataCondivisioneDa;
	}
	public void setDataCondivisioneDa(Date dataCondivisioneDa) {
		this.dataCondivisioneDa = dataCondivisioneDa;
	}
	public Date getDataCondivisioneA() {
		return dataCondivisioneA;
	}
	public void setDataCondivisioneA(Date dataCondivisioneA) {
		this.dataCondivisioneA = dataCondivisioneA;
	}
	public String getInseritoDa() {
		return inseritoDa;
	}
	public void setInseritoDa(String inseritoDa) {
		this.inseritoDa = inseritoDa;
	}
	public String getVisibileA() {
		return visibileA;
	}
	public void setVisibileA(String visibileA) {
		this.visibileA = visibileA;
	}
	public String getDestinatarioNotifica() {
		return destinatarioNotifica;
	}
	public void setDestinatarioNotifica(String destinatarioNotifica) {
		this.destinatarioNotifica = destinatarioNotifica;
	}
	public String getDescrizioneContenuto() {
		return descrizioneContenuto;
	}
	public void setDescrizioneContenuto(String descrizioneContenuto) {
		this.descrizioneContenuto = descrizioneContenuto;
	}
	public String getTitoloDocumento() {
		return titoloDocumento;
	}
	public void setTitoloDocumento(String titoloDocumento) {
		this.titoloDocumento = titoloDocumento;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public UUID getIdFascicolo() {
		return idFascicolo;
	}
	public void setIdFascicolo(UUID idFascicolo) {
		this.idFascicolo = idFascicolo;
	}
	
	
	
}
