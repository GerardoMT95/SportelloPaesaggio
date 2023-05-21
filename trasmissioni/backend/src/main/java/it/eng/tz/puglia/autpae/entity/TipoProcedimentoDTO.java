package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for table public.tipo_procedimento
 */
public class TipoProcedimentoDTO implements Serializable {

	private static final long serialVersionUID = 6693642738L;
	
	private String codice;
	private String descrizione;
	private boolean inviaMail;
	private boolean sanatoria;
	private Date inizioValidita;
	private Date fineValidita;
	private String applicativo;


	public TipoProcedimentoDTO() { }

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public boolean isInviaMail() {
		return inviaMail;
	}

	public void setInviaMail(boolean inviaMail) {
		this.inviaMail = inviaMail;
	}

	public boolean isSanatoria() {
		return sanatoria;
	}

	public void setSanatoria(boolean sanatoria) {
		this.sanatoria = sanatoria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + (inviaMail ? 1231 : 1237);
		result = prime * result + (sanatoria ? 1231 : 1237);
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
		TipoProcedimentoDTO other = (TipoProcedimentoDTO) obj;
		if (codice == null) {
			if (other.codice != null)
				return false;
		} else if (!codice.equals(other.codice))
			return false;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (inviaMail != other.inviaMail)
			return false;
		if (sanatoria != other.sanatoria)
			return false;
		
		if (inizioValidita == null) {
			if (other.inizioValidita != null) {
				return false;
			}
		} else if (!inizioValidita.equals(other.inizioValidita)) {
			return false;
		}
		
		if (fineValidita == null) {
			if (other.fineValidita != null) {
				return false;
			}
		} else if (!fineValidita.equals(other.fineValidita)) {
			return false;
		}
		
		if (applicativo == null) {
			if (other.applicativo != null) {
				return false;
			}
		} else if (!applicativo.equals(other.applicativo)) {
			return false;
		}
		return true;
	}


	@Override
	public String toString() {
		return "TipoProcedimentoDTO [codice=" + codice + ", descrizione=" + descrizione + ", inviaMail=" + inviaMail
				+ ", sanatoria=" + sanatoria + ", inizioValidita=" + inizioValidita + ", fineValidita=" + fineValidita
				+ ", applicativo=" + applicativo + "]";
	}

	public Date getFineValidita() {
		return fineValidita;
	}

	public void setFineValidita(Date fineValidita) {
		this.fineValidita = fineValidita;
	}

	public Date getInizioValidita() {
		return inizioValidita;
	}

	public void setInizioValidita(Date inizioValidita) {
		this.inizioValidita = inizioValidita;
	}
	

	public String getApplicativo() {
		return applicativo;
	}

	public void setApplicativo(String applicativo) {
		this.applicativo = applicativo;
	}

}
