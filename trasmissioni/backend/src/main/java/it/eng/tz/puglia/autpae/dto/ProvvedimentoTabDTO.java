package it.eng.tz.puglia.autpae.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.eng.tz.puglia.autpae.enumeratori.EsitoProvvedimento;

public class ProvvedimentoTabDTO implements Serializable
{
	private static final long serialVersionUID = 7545873978918968471L;

	private List<String> esitiPossibili;
	private AllegatoCustomDTO provvedimento;
	private AllegatoCustomDTO provvedimentoPrivato;
	private AllegatoCustomDTO parere;
	private AllegatoCustomDTO parerePrivato;
	
	
	public ProvvedimentoTabDTO() {
		this.esitiPossibili = new ArrayList<String>(3);
		this.esitiPossibili.add(EsitoProvvedimento.AUTORIZZATO.getTextValue());
		this.esitiPossibili.add(EsitoProvvedimento.NON_AUTORIZZATO.getTextValue());
		this.esitiPossibili.add(EsitoProvvedimento.AUT_CON_PRESCRIZ.getTextValue());
	}
	
	
	public List<String> getEsitiPossibili() {
		return esitiPossibili;
	}

	public void setEsitiPossibili(List<String> esitiPossibili) {
		this.esitiPossibili = esitiPossibili;
	}

	public AllegatoCustomDTO getProvvedimento() {
		return provvedimento;
	}

	public void setProvvedimento(AllegatoCustomDTO provvedimento) {
		this.provvedimento = provvedimento;
	}

	public AllegatoCustomDTO getParere() {
		return parere;
	}

	public void setParere(AllegatoCustomDTO parere) {
		this.parere = parere;
	}

	public AllegatoCustomDTO getProvvedimentoPrivato() {
		return provvedimentoPrivato;
	}

	public void setProvvedimentoPrivato(AllegatoCustomDTO provvedimentoPrivato) {
		this.provvedimentoPrivato = provvedimentoPrivato;
	}

	public AllegatoCustomDTO getParerePrivato() {
		return parerePrivato;
	}

	public void setParerePrivato(AllegatoCustomDTO parerePrivato) {
		this.parerePrivato = parerePrivato;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((esitiPossibili == null) ? 0 : esitiPossibili.hashCode());
		result = prime * result + ((parere == null) ? 0 : parere.hashCode());
		result = prime * result + ((parerePrivato == null) ? 0 : parerePrivato.hashCode());
		result = prime * result + ((provvedimento == null) ? 0 : provvedimento.hashCode());
		result = prime * result + ((provvedimentoPrivato == null) ? 0 : provvedimentoPrivato.hashCode());
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
		ProvvedimentoTabDTO other = (ProvvedimentoTabDTO) obj;
		if (esitiPossibili == null) {
			if (other.esitiPossibili != null)
				return false;
		} else if (!esitiPossibili.equals(other.esitiPossibili))
			return false;
		if (parere == null) {
			if (other.parere != null)
				return false;
		} else if (!parere.equals(other.parere))
			return false;
		if (parerePrivato == null) {
			if (other.parerePrivato != null)
				return false;
		} else if (!parerePrivato.equals(other.parerePrivato))
			return false;
		if (provvedimento == null) {
			if (other.provvedimento != null)
				return false;
		} else if (!provvedimento.equals(other.provvedimento))
			return false;
		if (provvedimentoPrivato == null) {
			if (other.provvedimentoPrivato != null)
				return false;
		} else if (!provvedimentoPrivato.equals(other.provvedimentoPrivato))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProvvedimentoTabDTO [esitiPossibili=" + esitiPossibili + ", provvedimento=" + provvedimento
				+ ", parere=" + parere + ", provvedimentoPrivato=" + provvedimentoPrivato + ", parerePrivato="
				+ parerePrivato + "]";
	}

}