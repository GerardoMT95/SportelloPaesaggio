package it.eng.tz.puglia.autpae.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class ProcedimentoQualificazioniDTO implements Serializable {

	private static final long serialVersionUID = 6693642738L;
	
	private String codiceTipoProcedimento;
	private Long idTipiQualificazioni;
	private Timestamp dateStart;
	private Timestamp dateEnd;
	private Boolean esclusoEti;
	
	public Timestamp getDateStart() {
		return dateStart;
	}

	public void setDateStart(Timestamp dateStart) {
		this.dateStart = dateStart;
	}

	public Timestamp getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Timestamp dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Boolean getEsclusoEti() {
		return esclusoEti;
	}

	public void setEsclusoEti(Boolean esclusoEti) {
		this.esclusoEti = esclusoEti;
	}

	public ProcedimentoQualificazioniDTO() { }

	public String getCodiceTipoProcedimento() {
		return codiceTipoProcedimento;
	}
	public void setCodiceTipoProcedimento(String codiceTipoProcedimento) {
		this.codiceTipoProcedimento = codiceTipoProcedimento;
	}
	public Long getIdTipiQualificazioni() {
		return idTipiQualificazioni;
	}
	public void setIdTipiQualificazioni(Long idTipiQualificazioni) {
		this.idTipiQualificazioni = idTipiQualificazioni;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codiceTipoProcedimento == null) ? 0 : codiceTipoProcedimento.hashCode());
		result = prime * result + ((dateEnd == null) ? 0 : dateEnd.hashCode());
		result = prime * result + ((dateStart == null) ? 0 : dateStart.hashCode());
		result = prime * result + ((esclusoEti == null) ? 0 : esclusoEti.hashCode());
		result = prime * result + ((idTipiQualificazioni == null) ? 0 : idTipiQualificazioni.hashCode());
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
		ProcedimentoQualificazioniDTO other = (ProcedimentoQualificazioniDTO) obj;
		if (codiceTipoProcedimento == null) {
			if (other.codiceTipoProcedimento != null)
				return false;
		} else if (!codiceTipoProcedimento.equals(other.codiceTipoProcedimento))
			return false;
		if (dateEnd == null) {
			if (other.dateEnd != null)
				return false;
		} else if (!dateEnd.equals(other.dateEnd))
			return false;
		if (dateStart == null) {
			if (other.dateStart != null)
				return false;
		} else if (!dateStart.equals(other.dateStart))
			return false;
		if (esclusoEti == null) {
			if (other.esclusoEti != null)
				return false;
		} else if (!esclusoEti.equals(other.esclusoEti))
			return false;
		if (idTipiQualificazioni == null) {
			if (other.idTipiQualificazioni != null)
				return false;
		} else if (!idTipiQualificazioni.equals(other.idTipiQualificazioni))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProcedimentoQualificazioniDTO [codiceTipoProcedimento=" + codiceTipoProcedimento
				+ ", idTipiQualificazioni=" + idTipiQualificazioni + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd
				+ ", esclusoEti=" + esclusoEti + "]";
	}


}
