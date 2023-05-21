package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.jasper;

import java.io.Serializable;

import it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.dto.TipoProcedimentoDTO;

/**
 * allineamento al bean del vecchio subreport jasper AS IS .. Istanza_Pratica.jrxml o Dichiarazione_Tecnica_Pratica.jrxml 
 * @author acolaianni
 *
 */
public class JasperTipoProcedimentoDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codice;
	private String descrizione;
	private String descrstampa;
	private String descrstampatitolo;
	private String descrstampasottotitolo;
	/**
	 * @return the codice
	 */
	public String getCodice() {
		return codice;
	}
	/**
	 * @param codice the codice to set
	 */
	public void setCodice(String codice) {
		this.codice = codice;
	}
	/**
	 * @return the descrizione
	 */
	public String getDescrizione() {
		return descrizione;
	}
	/**
	 * @param descrizione the descrizione to set
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	/**
	 * @return the descrstampa
	 */
	public String getDescrstampa() {
		return descrstampa;
	}
	/**
	 * @param descrstampa the descrstampa to set
	 */
	public void setDescrstampa(String descrstampa) {
		this.descrstampa = descrstampa;
	}
	/**
	 * @return the descrstampatitolo
	 */
	public String getDescrstampatitolo() {
		return descrstampatitolo;
	}
	/**
	 * @param descrstampatitolo the descrstampatitolo to set
	 */
	public void setDescrstampatitolo(String descrstampatitolo) {
		this.descrstampatitolo = descrstampatitolo;
	}
	/**
	 * @return the descrstampasottotitolo
	 */
	public String getDescrstampasottotitolo() {
		return descrstampasottotitolo;
	}
	/**
	 * @param descrstampasottotitolo the descrstampasottotitolo to set
	 */
	public void setDescrstampasottotitolo(String descrstampasottotitolo) {
		this.descrstampasottotitolo = descrstampasottotitolo;
	}
	
	public JasperTipoProcedimentoDto() {
		super();
	}
	
	public JasperTipoProcedimentoDto(TipoProcedimentoDTO tipoprocDto) {
		super();
		this.codice = tipoprocDto.getId()+"";
		this.descrizione = tipoprocDto.getDescrizione();
		this.descrstampa = tipoprocDto.getDescrStampa();
		this.descrstampatitolo = tipoprocDto.getDescrStampaTitolo();
		this.descrstampasottotitolo = tipoprocDto.getDescrStampaSottoTitolo();
	}
}
