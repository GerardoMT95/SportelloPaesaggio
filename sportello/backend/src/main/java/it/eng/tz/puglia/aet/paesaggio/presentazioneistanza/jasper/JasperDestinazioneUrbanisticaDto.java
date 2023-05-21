package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.util.Date;

public class JasperDestinazioneUrbanisticaDto {
	
	private String nomeComune;
	private boolean mostraCoerenza;
	private Date coerenzaData;
	private String coerenzaAtto;
	
	private Integer strumentoUrbanistico;
	private Date approvatoData;
	private String approvatoCon;
	private String destinazioneUrbanistica;
	private String ulterioriTutele;
	private Boolean confermaCoerenza;
	
	private Integer strumentoInAdozione;
	private Date adottatoData;
	private String adottatoCon;
	private String destinazioneUrbanisticaAdottato;
	private String ulterioriTuteleAdottato;
	private Boolean conformitaStrumentoUrbanistico;
	
	public JasperDestinazioneUrbanisticaDto() {
		super();
	}
	
	public String getNomeComune() {
		return nomeComune;
	}
	public void setNomeComune(String nomeComune) {
		this.nomeComune = nomeComune;
	}
	public boolean isMostraCoerenza() {
		return mostraCoerenza;
	}
	public void setMostraCoerenza(boolean mostraCoerenza) {
		this.mostraCoerenza = mostraCoerenza;
	}
	public Date getCoerenzaData() {
		return coerenzaData;
	}
	public void setCoerenzaData(Date coerenzaData) {
		this.coerenzaData = coerenzaData;
	}
	public String getCoerenzaAtto() {
		return coerenzaAtto;
	}
	public void setCoerenzaAtto(String coerenzaAtto) {
		this.coerenzaAtto = coerenzaAtto;
	}
	public Integer getStrumentoUrbanistico() {
		return strumentoUrbanistico;
	}
	public void setStrumentoUrbanistico(Integer strumentoUrbanistico) {
		this.strumentoUrbanistico = strumentoUrbanistico;
	}
	public Date getApprovatoData() {
		return approvatoData;
	}
	public void setApprovatoData(Date approvatoData) {
		this.approvatoData = approvatoData;
	}
	public String getApprovatoCon() {
		return approvatoCon;
	}
	public void setApprovatoCon(String approvatoCon) {
		this.approvatoCon = approvatoCon;
	}
	public String getDestinazioneUrbanistica() {
		return destinazioneUrbanistica;
	}
	public void setDestinazioneUrbanistica(String destinazioneUrbanistica) {
		this.destinazioneUrbanistica = destinazioneUrbanistica;
	}
	public String getUlterioriTutele() {
		return ulterioriTutele;
	}
	public void setUlterioriTutele(String ulterioriTutele) {
		this.ulterioriTutele = ulterioriTutele;
	}
	public Boolean getConfermaCoerenza() {
		return confermaCoerenza;
	}
	public void setConfermaCoerenza(Boolean confermaCoerenza) {
		this.confermaCoerenza = confermaCoerenza;
	}
	public Integer getStrumentoInAdozione() {
		return strumentoInAdozione;
	}
	public void setStrumentoInAdozione(Integer strumentoInAdozione) {
		this.strumentoInAdozione = strumentoInAdozione;
	}
	public Date getAdottatoData() {
		return adottatoData;
	}
	public void setAdottatoData(Date adottatoData) {
		this.adottatoData = adottatoData;
	}
	public String getAdottatoCon() {
		return adottatoCon;
	}
	public void setAdottatoCon(String adottatoCon) {
		this.adottatoCon = adottatoCon;
	}
	public String getDestinazioneUrbanisticaAdottato() {
		return destinazioneUrbanisticaAdottato;
	}
	public void setDestinazioneUrbanisticaAdottato(String destinazioneUrbanisticaAdottato) {
		this.destinazioneUrbanisticaAdottato = destinazioneUrbanisticaAdottato;
	}
	public String getUlterioriTuteleAdottato() {
		return ulterioriTuteleAdottato;
	}
	public void setUlterioriTuteleAdottato(String ulterioriTuteleAdottato) {
		this.ulterioriTuteleAdottato = ulterioriTuteleAdottato;
	}
	public Boolean getConformitaStrumentoUrbanistico() {
		return conformitaStrumentoUrbanistico;
	}
	public void setConformitaStrumentoUrbanistico(Boolean conformitaStrumentoUrbanistico) {
		this.conformitaStrumentoUrbanistico = conformitaStrumentoUrbanistico;
	}
	
}
