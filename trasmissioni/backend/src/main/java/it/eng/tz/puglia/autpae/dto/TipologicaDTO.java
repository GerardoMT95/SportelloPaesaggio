package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;

import it.eng.tz.puglia.autpae.dto.BE_to_FE.PlainTypeStringIdDTO;

public class TipologicaDTO implements Serializable
{
	private static final long serialVersionUID = 7545873978918968471L;

	private String codice;  // per la localizzazione diventa "codice_parchi\aree\paesaggi"
	private String label;   // per la localizzazione diventa "codice_istat"
	
	
	public TipologicaDTO() { }
	
	public TipologicaDTO(String codice, String label) {
		this.codice = codice;
		this.label  = label;
	}

//	public TipologicaDTO(TemplateDestinatarioDTO destinatario) {
//		this.codice = destinatario.getEmail();
//		this.label  = destinatario.getDenominazione();
//	}

	public TipologicaDTO(PlainTypeStringIdDTO plainTypeStringIdDto) {
		this.codice = plainTypeStringIdDto.getId();
		this.label  = plainTypeStringIdDto.getNome();
	}
	
	public TipologicaDTO(TipologicaDestinatarioDTO other)
	{
		this.codice = other.getEmail();
		this.label = other.getNome();
	}
	
	
	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
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
		TipologicaDTO other = (TipologicaDTO) obj;
		if (codice == null) {
			if (other.codice != null)
				return false;
		} else if (!codice.equals(other.codice))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TipologicaDTO [codice=" + codice + ", label=" + label + "]";
	}
	
}
