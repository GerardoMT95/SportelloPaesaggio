package it.eng.tz.puglia.autpae.entity.composedKeys;

import java.io.Serializable;
import java.sql.Timestamp;

public class ProcedimentoQualificazioniPK implements Serializable {

	private static final long serialVersionUID = 7937074150880563920L;
	
	private String codiceTipoProcedimento;
	private Long idTipiQualificazioni;
	private Timestamp dateStart;
	
	public Timestamp getDateStart() {
		return dateStart;
	}

	public void setDateStart(Timestamp dateStart) {
		this.dateStart = dateStart;
	}

	public ProcedimentoQualificazioniPK() { }

	public ProcedimentoQualificazioniPK(String codiceTipoProcedimento, Long idTipiQualificazioni) {
		this.codiceTipoProcedimento = codiceTipoProcedimento;
		this.idTipiQualificazioni = idTipiQualificazioni;
	}

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
		result = prime * result + ((dateStart == null) ? 0 : dateStart.hashCode());
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
		ProcedimentoQualificazioniPK other = (ProcedimentoQualificazioniPK) obj;
		if (codiceTipoProcedimento == null) {
			if (other.codiceTipoProcedimento != null)
				return false;
		} else if (!codiceTipoProcedimento.equals(other.codiceTipoProcedimento))
			return false;
		if (dateStart == null) {
			if (other.dateStart != null)
				return false;
		} else if (!dateStart.equals(other.dateStart))
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
		return "ProcedimentoQualificazioniPK [codiceTipoProcedimento=" + codiceTipoProcedimento
				+ ", idTipiQualificazioni=" + idTipiQualificazioni + ", dateStart=" + dateStart + "]";
	}

	

}
