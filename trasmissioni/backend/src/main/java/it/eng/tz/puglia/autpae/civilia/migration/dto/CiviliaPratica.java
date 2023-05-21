/**
 * 
 */
package it.eng.tz.puglia.autpae.civilia.migration.dto;

import java.util.Date;

/**
 * tabella CIVILIA_XXX.T_PRATICA
 * @author Adriano Colaianni
 * @date 30 lug 2021
 */
public class CiviliaPratica {
	private String codice;
	private Date dataAttivazione; //data creazione sempre popolata!!!
	private Date dataArrivo;
	private int statoPratica;
	
	private Long tPraticaId;
	
	/*per CIVILIA_CS contiene un id del tipo intervento:
	197904	S2	ristrutturazione edilizia (art. 3 DPR 380/01)
	197905	S3	nuova costruzione (art. 3 DPR 380/01)
	197906	S4	interventi e/o opere di grande impegno territoriale (DPCM 12/12/2005)
	197907	S5	interventi e/o opere non di edilizia
	197903	S1	manutenzione, restauro e risanamento conservativo (art. 3 DPR 380/01)
	, per CIVILIA_016 solo 1 valore 
	32062 ND 	non definito!*/
	private Long tAgsuTipoInterventoId; 
	
	private String codiceInterno; //a volte pieno
	private String pratCodNum1;//es.AP70123
	private Long pratCodNum2;//14
	private Long pratCodNum3;//2011
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public Date getDataAttivazione() {
		return dataAttivazione;
	}
	public void setDataAttivazione(Date dataAttivazione) {
		this.dataAttivazione = dataAttivazione;
	}
	public Date getDataArrivo() {
		return dataArrivo;
	}
	public void setDataArrivo(Date dataArrivo) {
		this.dataArrivo = dataArrivo;
	}
	public int getStatoPratica() {
		return statoPratica;
	}
	public void setStatoPratica(int statoPratica) {
		this.statoPratica = statoPratica;
	}
	public Long gettPraticaId() {
		return tPraticaId;
	}
	public void settPraticaId(Long tPraticaId) {
		this.tPraticaId = tPraticaId;
	}
	public Long gettAgsuTipoInterventoId() {
		return tAgsuTipoInterventoId;
	}
	public void settAgsuTipoInterventoId(Long tAgsuTipoInterventoId) {
		this.tAgsuTipoInterventoId = tAgsuTipoInterventoId;
	}
	public String getCodiceInterno() {
		return codiceInterno;
	}
	public void setCodiceInterno(String codiceInterno) {
		this.codiceInterno = codiceInterno;
	}
	public String getPratCodNum1() {
		return pratCodNum1;
	}
	public void setPratCodNum1(String pratCodNum1) {
		this.pratCodNum1 = pratCodNum1;
	}
	public Long getPratCodNum2() {
		return pratCodNum2;
	}
	public void setPratCodNum2(Long pratCodNum2) {
		this.pratCodNum2 = pratCodNum2;
	}
	public Long getPratCodNum3() {
		return pratCodNum3;
	}
	public void setPratCodNum3(Long pratCodNum3) {
		this.pratCodNum3 = pratCodNum3;
	}
	

}
