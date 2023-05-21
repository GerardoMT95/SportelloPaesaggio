package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;

public class TipiEQualificazioniDTO implements Serializable {

	private static final long serialVersionUID = -3388340747445738923L;

	private Long id;
	private String stile;
	private Integer zona;
	private String label;
	private Integer ordine;
	private String raggruppamento;
	
//	private Boolean selezionato;   // non fa parte della tabella

	
	public TipiEQualificazioniDTO() { }
	
	public TipiEQualificazioniDTO(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStile() {
		return stile;
	}

	public void setStile(String stile) {
		this.stile = stile;
	}

	public Integer getZona() {
		return zona;
	}

	public void setZona(Integer zona) {
		this.zona = zona;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getOrdine() {
		return ordine;
	}

	public void setOrdine(Integer ordine) {
		this.ordine = ordine;
	}

//	public Boolean getSelezionato() {
//		return selezionato;
//	}

//	public void setSelezionato(Boolean selezionato) {
//		this.selezionato = selezionato;
//	}
	
	public String getRaggruppamento() {
		return raggruppamento;
	}

	public void setRaggruppamento(String raggruppamento) {
		this.raggruppamento = raggruppamento;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((ordine == null) ? 0 : ordine.hashCode());
		result = prime * result + ((stile == null) ? 0 : stile.hashCode());
		result = prime * result + ((zona == null) ? 0 : zona.hashCode());
		result = prime * result + ((raggruppamento == null) ? 0 : raggruppamento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipiEQualificazioniDTO other = (TipiEQualificazioniDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (raggruppamento == null) {
			if (other.raggruppamento != null)
				return false;
		} else if (!raggruppamento.equals(other.raggruppamento))
			return false;
		if (ordine == null) {
			if (other.ordine != null)
				return false;
		} else if (!ordine.equals(other.ordine))
			return false;
		if (stile == null) {
			if (other.stile != null)
				return false;
		} else if (!stile.equals(other.stile))
			return false;
		if (zona == null) {
			if (other.zona != null)
				return false;
		} else if (!zona.equals(other.zona))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TipiEQualificazioniDTO [id=" + id + ", stile=" + stile + ", zona=" + zona + ", label=" + label
				+ ", ordine=" + ordine + ", raggruppamento=" + raggruppamento + "]";
	}

}
