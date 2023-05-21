package it.eng.tz.puglia.aet.paesaggio.presentazioneistanza.orm.search;

import it.eng.tz.puglia.bean.BaseSearchRequestBean;

public class SezioneCatastaleSearchDTO extends BaseSearchRequestBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome; //campo di ricerca su piu' colonne
	
	private String codCatastale;
	private String sezione;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodCatastale() {
		return codCatastale;
	}

	public void setCodCatastale(String codCatastale) {
		this.codCatastale = codCatastale;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

}
